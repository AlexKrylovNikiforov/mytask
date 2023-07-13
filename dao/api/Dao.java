package mytask.dao.api;

import java.util.List;

public interface Dao<T> {

    T getById(int id);
    T save(T entity);
    void deleteById(int id);
    void delete(T entity);
    List<T> getAll();
}
