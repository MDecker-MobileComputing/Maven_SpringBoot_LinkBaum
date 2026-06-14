package de.eldecker.spring.linkbaum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


/**
 * Hauptklasse der Anwendung, die Spring Boot startet.
 */
@SpringBootApplication
@EnableRedisRepositories(basePackages = "de.eldecker.spring.linkbaum.db")
public class LinkBaumApplication {

	public static void main( String[] args ) {

		SpringApplication.run( LinkBaumApplication.class, args );
	}

}
