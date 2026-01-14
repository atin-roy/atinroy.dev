package dev.atinroy.notesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot application. SpringApplication.run boots the embedded server
 * and wires all the beans (like NoteController and NoteService).
 */
@SpringBootApplication
public class NotesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesBackendApplication.class, args);
	}

}
