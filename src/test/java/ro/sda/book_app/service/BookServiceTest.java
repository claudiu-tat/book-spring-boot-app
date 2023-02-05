package ro.sda.book_app.service;


import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.sda.book_app.exception.NotFoundException;
import ro.sda.book_app.model.Book;
import ro.sda.book_app.repository.BookRepository;
import ro.sda.book_app.service.interfaces.BookService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    BookService bookService;
    @Mock                               // with @Mock, we bring a dummy object, and we can use all the methods of it, otherwise we have to make an anonymous class and bring all the methods inline
    BookRepository bookRepository;

    LogCaptor logCaptor;

    @BeforeEach
    void init() {
        bookService = new BookServiceImpl(bookRepository);
        logCaptor = LogCaptor.forClass(BookServiceImpl.class);
    }

    @Test
    void testSaveMethod() {
        Book book = new Book(1, "a", "b", 2000);
        Mockito.lenient().when(bookRepository.save(any()))
                .thenReturn(book);
        bookService.save(book);
        verify(bookRepository, times(1)).save(any());

        assertThat(logCaptor.getInfoLogs()).containsExactly("Saving book: Book(id=1, title=a, author=b, year=2000)");

    }

    @Test
    void testFindAll_emptyList_throwsEx() {

        Mockito.lenient().when(bookRepository.findAll())
                .thenReturn(Collections.emptyList());

        Assertions.assertThrows(NotFoundException.class, () -> {
            bookService.findAll();
        });

        verify(bookRepository, times(1)).findAll();

        assertThat(logCaptor.getInfoLogs()).containsExactly("No books were found!");

    }

    @Test
    void testFindAll_returnListOfBooks() {
        List<Book> bookList = List.of(
                new Book(1, "a", "b",2000),
                new Book(2,"x","y",2022)
        );

        Mockito.lenient().when(bookRepository.findAll())
                .thenReturn(bookList);

        List<Book> result = bookService.findAll();

        assertThat(result).containsAll(bookList);

        verify(bookRepository, times(1)).findAll();

        assertThat(logCaptor.getInfoLogs()).containsExactly("Books found in the database!");

    }
}
