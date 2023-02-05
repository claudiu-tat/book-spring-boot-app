package ro.sda.book_app.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sda.book_app.model.Book;
import ro.sda.book_app.service.interfaces.BookService;

import java.util.List;

@RestController
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/book")
    public ResponseEntity<Void> save(@Valid @RequestBody Book book) {
        bookService.save(book);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping(value = "/book")
    public ResponseEntity<Void> update(@Valid @RequestBody Book book) {
        bookService.update(book);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    /*@PostMapping(value = "/v2/book")                  Another way to work with responses
    @ResponseStatus(HttpStatus.CREATED)
    public void anotherSave(@RequestBody Book book) {
        bookService.save(book);
    }*/

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") long id) {

        Book result = bookService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping(value = "/books")
    public ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @DeleteMapping("/book")
    public ResponseEntity<String> deleteById(@RequestParam(name = "id", required = true) long id) {
        bookService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
