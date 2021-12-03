package repository;

/**
 * declare create, getAll, update, delete functions
 *
 * @param <T>
 */
public interface ICrudRepository<T> {

    T findOne(T obj);

    Iterable<T> findAll();

    T save(T obj);

    T update(T obj);

    T delete(T obj);
}
