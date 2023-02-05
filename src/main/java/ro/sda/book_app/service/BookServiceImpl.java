package ro.sda.book_app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.sda.book_app.exception.NotFoundException;
import ro.sda.book_app.model.Book;
import ro.sda.book_app.repository.BookRepository;
import ro.sda.book_app.service.interfaces.BookService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void save(Book book) {
        log.info("Saving book: {}", book);
        bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            log.info("No books were found!");
            throw new NotFoundException("No books were found!");
        }
        log.info("Books found in the database!");
        return books;
    }

    @Override
    public void update(Book book) {
        log.info("Fetching book with id: {}", book.getId());
        bookRepository.findById(book.getId()).orElseThrow(() -> new NotFoundException(String.format("Book with id (%s) could not be found", book.getId())));
        log.info("Updating book with id: {}", book.getId());
        bookRepository.save(book);
    }

    @Override
    public void deleteById(long id) {
        log.info("Fetching book with id: {}", id);
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new NotFoundException(String.format("Book with id (%s) could not be found", id));
        }

        log.info("Deleting book wth id: {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public Book findById(long id) {
        log.info("Fetching book with id: {}", id);
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            throw new NotFoundException(String.format("Book with id (%s) could not be found", id));
        }
    }

    public Book findByIdFunctional(long id) {
        log.info("Fetching book with id: {}", id);
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Book with id (%s) could not be found", id)));
    }
}
