package ro.sda.book_app.service.interfaces;

import java.util.List;

public interface GenericService<T> {
    void save(T t);
    List<T> findAll();
    void update(T t);
    void deleteById(long id);
    T findById(long id);
}
