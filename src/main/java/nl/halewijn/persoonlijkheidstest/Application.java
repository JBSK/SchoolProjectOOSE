package nl.halewijn.persoonlijkheidstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class Application {

    @PersistenceConstructor
	public Application() {
		/*
		 * This is a comment,
		 * it stands here proudly,
		 * warding off errors from Sonar.
		 */
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}