package biblio.controller;

import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import biblio.dto.NewBook;
import biblio.model.Book;
import biblio.resource.BookResource;
import biblio.service.BookService;

@RestController
@RequestMapping(ApiPaths.LIBRARY_BOOKS)
@ExposesResourceFor(BookResource.class)
@Transactional // TODO replace this with a better way of working with the persistence layer
public class BookController implements SearchableController<BookResource> {
	
	@Autowired private BookService bookService;
	
	@Override
	public Resources<BookResource> search(@RequestParam Map<String, String> params) {
		return bookService.getBooks(params);
	}
	
	@RequestMapping(value = "/{webId}", method = RequestMethod.GET)
	public BookResource getBook(@PathVariable("webId") String webId, @RequestParam(name = "detailed", defaultValue = "false") boolean detailed) {
		Book book = bookService.getBook(webId, detailed);
		return bookService.toResource(book, true);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public BookResource addBook(@RequestBody @Valid NewBook newBook) {
		Book book = bookService.addBook(newBook);
		return bookService.toResource(book, true);
	}
	
	@RequestMapping(value = "/{webId}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> deleteBook(@PathVariable("webId") String webId) {
		if (bookService.webIdExists(webId)) {
			bookService.deleteBook(webId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
