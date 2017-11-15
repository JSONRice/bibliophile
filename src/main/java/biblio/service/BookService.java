package biblio.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import biblio.controller.BookController;
import biblio.dto.NewBook;
import biblio.model.Author;
import biblio.model.Book;
import biblio.model.BookFormat;
import biblio.model.BookSubject;
import biblio.model.Publisher;
import biblio.repository.AuthorRepository;
import biblio.repository.BookRepository;
import biblio.repository.PublisherRepository;
import biblio.repository.BookSubjectRepository;
import biblio.resource.BookResource;
import biblio.service.resourceAssembler.BookResourceAssembler;
import biblio.util.PageResourceUtils;
import biblio.util.ResourceUtils;
import biblio.util.SearchUtils;
import biblio.util.WebIdUtils;
import biblio.validation.WebIdExists;

@Service
public class BookService implements WebIdExists {
	
	@Autowired private BookResourceAssembler bookResourceAssembler;
	
	@Autowired private BookRepository bookRepository;
	@Autowired private AuthorRepository authorRepository;
	@Autowired private PublisherRepository publisherRepository;
	@Autowired private BookSubjectRepository bookSubjectRepository;
	
	private Map<String, String> sortTermMap = new HashMap<>();
	
	public BookService() {
		// Populate map with valid sort terms and the DB columns they map to.
		// As other sorting terms are supported, add them here.
		sortTermMap.put("title", "title");
		sortTermMap.put("year", "publishYear");
	}
	
	public Resources<BookResource> getBooks(Map<String, String> params) {
		Pageable pageRequest = SearchUtils.paramsToPageRequest(params, sortTermMap);
		Page<Book> results;
		
		if (SearchUtils.hasSearchParam(params)) {
			String searchTerm = SearchUtils.getSearchParam(params);
			results = bookRepository.findByTitleContainsOrSummaryContainsAllIgnoreCase(searchTerm, searchTerm, pageRequest);
		} else {
			results = bookRepository.findAll(pageRequest);
		}
		
		Resources<BookResource> resource = PageResourceUtils.pageToResources(results, bookResourceAssembler::toResource);
		
		PageResourceUtils.addNextPrevPageLinks(resource, results, params, BookController.class);
		
		return resource;
	}
	
	public Book getBook(String webId, boolean includeCollections) {
		return ResourceUtils.entityOrNotFoundException(bookRepository.findOneByWebId(webId));
	}
	
	public BookResource toResource(Book book, boolean isDetailed) {
		BookResource resource = bookResourceAssembler.toResource(book);
		bookResourceAssembler.addActionLinks(resource, book);
		if (isDetailed) {
			resource.addDetails(book);
			bookResourceAssembler.addEmbeddedResources(resource, book);
		} else {
			bookResourceAssembler.addResourceLinks(resource, book);
		}
		return resource;
	}
	
	public Book addBook(NewBook newBook) {
		Book book = new Book();
		book.setWebId(WebIdUtils.nextBookId());
		book.setTitle(newBook.getTitle());
		book.setFormat(BookFormat.of(newBook.getFormatCode()));
		book.setSummary(newBook.getSummary());
		book.setPublishYear(newBook.getYear());
		
		// add existing authors
		List<String> authorIds = newBook.getAuthors()
				.stream()
				.map(NewBook.Author::getId)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		book.getAuthors().addAll(authorRepository.findByWebIdIn(authorIds));
		
		// create and add new authors
		List<Author> newAuthors = newBook.getAuthors()
				.stream()
				.filter(author -> author.getId() == null)
				.filter(author -> author.getName() != null)
				.map(Author::new)
				.map(authorRepository::save)
				.collect(Collectors.toList());
		book.getAuthors().addAll(newAuthors);
		
		// add existing subjects
		List<String> subjectIds = newBook.getSubjects()
				.stream()
				.map(NewBook.Subject::getId)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		book.getSubjects().addAll(bookSubjectRepository.findByWebIdIn(subjectIds));
		
		// create and add new subjects
		List<BookSubject> newSubjects = newBook.getSubjects()
				.stream()
				.filter(subj -> subj.getId() == null)
				.filter(subj -> subj.getName() != null)
				.map(BookSubject::new)
				.map(bookSubjectRepository::save)
				.collect(Collectors.toList());
		book.getSubjects().addAll(newSubjects);
		
		// add existing or create new publisher
		if (newBook.getPublisher().getId() != null) {
			book.setPublisher(publisherRepository.findOneByWebId(newBook.getPublisher().getId()).orElse(null));
		} else if (newBook.getPublisher().getName() != null) {
			Publisher publisher = new Publisher(newBook.getPublisher());
			book.setPublisher(publisherRepository.save(publisher));
		}
		
		return bookRepository.save(book);
	}
	
	public void deleteBook(String webId) {
		bookRepository.findOneByWebId(webId).ifPresent(bookRepository::delete);
	}
	
	@Override
	public boolean webIdExists(String webId) {
		return bookRepository.existsByWebId(webId);
	}
	
	@Override
	public boolean webIdExists(List<String> webIds) {
		return webIds.stream().allMatch(bookRepository::existsByWebId);
	}
}
