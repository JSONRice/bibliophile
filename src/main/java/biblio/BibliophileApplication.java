package biblio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableEntityLinks;

@SpringBootApplication
@EnableEntityLinks
public class BibliophileApplication {
	public static void main(String[] args) {
	    try {
            SpringApplication.run(BibliophileApplication.class, args);
        } catch(Exception e) {
	        System.err.println("Caught: " + e.getMessage());
        }
	}
}
