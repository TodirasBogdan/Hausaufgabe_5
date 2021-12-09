package repository;

import java.io.IOException;

/**
 * declare findOne, findAll, save, update, delete functions
 *
 * @param <T>
 */
public interface ICrudRepository<T> {

    T findOne(T obj) throws IOException;

    Iterable<T> findAll() throws IOException;

    void save(T obj) throws IOException;

    void update(T obj) throws IOException;

    T delete(T obj) throws IOException;
}
