package biblio.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SearchUtils {
	
	public static final String SEARCH = "q";
	public static final String SORT = "sort";
	public static final String PAGE_START = "start";
	public static final String PAGE_START_DEFAULT = "0";
	public static final String PAGE_LIMIT = "limit";
	public static final String PAGE_LIMIT_DEFAULT = "10";
	
	private SearchUtils() {}
	
	public static Pageable paramsToPageRequest(Map<String, String> params, Map<String, String> sortTermMap) {
		String pageString = Optional.ofNullable(params.get(PAGE_START)).orElse(PAGE_START_DEFAULT);
		String limitString = Optional.ofNullable(params.get(PAGE_LIMIT)).orElse(PAGE_LIMIT_DEFAULT);
		
		Function<String, Sort> termToSort = term -> {
			Direction dir = term.startsWith("-") ? Direction.DESC : Direction.ASC;
			return new Sort(dir, sortTermMap.get(term));
		};
		
		Optional<Sort> sortOptional = Optional.ofNullable(params.get(SORT)).flatMap(string -> {
			return Arrays.stream(string.split(","))
			.map(String::toLowerCase)
			.filter(sortTermMap::containsKey)
			.map(termToSort)
			.reduce(Sort::and);
		});

		return sortOptional
				.map(sort -> new PageRequest(Integer.valueOf(pageString), Integer.valueOf(limitString), sort))
				.orElse(new PageRequest(Integer.valueOf(pageString), Integer.valueOf(limitString)));
	}
	
	public static Map<String, String> nextPageParams(Map<String, String> params, int nextPage) {
		Map<String, String> newParams = new HashMap<>(params);
		newParams.put(PAGE_START, Integer.toString(nextPage));
		return newParams;
	}
	
	public static boolean hasSearchParam(Map<String, String> params) {
		return params.containsKey(SEARCH);
	}
	
	public static String getSearchParam(Map<String, String> params) {
		return Objects.requireNonNull(params.get(SEARCH));
	}
}
