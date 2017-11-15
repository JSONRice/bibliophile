package biblio.util;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Random;

import biblio.model.*;

public class WebIdUtils {
	
	private static final Integer BOOK_ID_LENGTH = 10;
	private static final Integer BOOK_COPY_ID_LENGTH = 10;
	private static final Integer AUTHOR_ID_LENGTH = 8;
	private static final Integer SUBJECT_ID_LENGTH = 6;
	private static final Integer PUBLISHER_ID_LENGTH = 6;

	private static Random random = new Random();
	private static Encoder encoder = Base64.getUrlEncoder();
	
	private WebIdUtils() {}
	
	public static String nextBookId() {
		return generateRandomString(BOOK_ID_LENGTH);
	}
	
	public static String nextBookCopyId() {
		return generateRandomString(BOOK_COPY_ID_LENGTH);
	}
	
	public static String nextAuthorId() {
		return generateRandomString(AUTHOR_ID_LENGTH);
	}
	
	public static String nextSubjectId() {
		return generateRandomString(SUBJECT_ID_LENGTH);
	}
	
	public static String nextPublisherId() {
		return generateRandomString(PUBLISHER_ID_LENGTH);
	}

	private static String generateRandomString(int length) {
		// Need 3 bytes per 4 characters of output, rounded up
		byte[] bytes = new byte[((length + 3) / 4 * 3)];
		random.nextBytes(bytes);
		return encoder.encodeToString(bytes).substring(0, length);
	}
}
