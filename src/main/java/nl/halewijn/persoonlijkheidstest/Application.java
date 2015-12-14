package nl.halewijn.persoonlijkheidstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.PersistenceConstructor;

@SpringBootApplication
public class Application {
	@PersistenceConstructor
	public Application() {
		
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}