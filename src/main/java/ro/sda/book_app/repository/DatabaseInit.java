package ro.sda.book_app.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.sda.book_app.model.Book;

import java.util.Arrays;
import java.util.List;


@Component
@Slf4j
public class DatabaseInit implements CommandLineRunner {

    // with this layer we initialize a dummy database to avoiding to write everytime new objects to testing
    private BookRepository bookRepository;

    public DatabaseInit(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Book> books = Arrays.asList(
                Book.builder().title("Why Buddhism is True").author("Robert Write").year(2017).build(),
                Book.builder().title("Atomic Habits").author("James Clear").year(2018).build(),
                Book.builder().title("Amintri din copilarie").author("Ion Creanga").year(1892).build(),
                Book.builder().title("The Kubernetes book").author("Nigel Poulton").year(2017).build()
        );

        bookRepository.saveAll(books);
        log.info("Books were created: {}", books);
    }
}
