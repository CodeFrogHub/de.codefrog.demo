package de.codefrog.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.IntStream;

@SpringBootApplication
public class DemoApplication {

    @Bean
    CommandLineRunner runner(TodoRepository todoRepository) {
        return args -> {
            IntStream.range(1, 1000).forEach(i -> {
                todoRepository.save(new Todo("TODO "+i, LocalDate.now().plusDays(i)));
            });
            todoRepository.findAll().forEach(System.out::println);
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

@RepositoryRestResource
interface TodoRepository extends JpaRepository<Todo, Long> {
    @RestResource(path = "by-description")
    Collection<Todo> findByDescription(@Param("description") String description);
}

@Entity
class Todo {
    @Id @GeneratedValue
    private Long id;
    private String description;
    private LocalDate due;

    public Todo() {
    }

    public Todo(String description, LocalDate due) {
        this.description = description;
        this.due = due;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }
}