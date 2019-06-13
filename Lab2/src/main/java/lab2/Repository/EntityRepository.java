package lab2.Repository;

import org.springframework.data.domain.Example;

import java.util.List;

public interface EntityRepository<T, ID> {
    List<T> findAll(Example<T> employee);
    <S extends T> S save(S entity);
    void delete(T entity);
}