package ro.sda.book_app.service.interfaces;

import ro.sda.book_app.model.Book;

import java.util.List;

public interface BookService extends GenericService<Book>{
    @Override
    void save(Book book);

    @Override
    List<Book> findAll();

    @Override
    void update(Book book);

    @Override
    void deleteById(long id);

    @Override
    Book findById(long id);
}
