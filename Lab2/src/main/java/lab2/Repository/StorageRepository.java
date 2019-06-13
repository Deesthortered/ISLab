package lab2.Repository;

import lab2.Entity.Storage;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StorageRepository extends CrudRepository<Storage, Long>, EntityRepository<Storage, Long> {
    List<Storage> findAll(Example<Storage> employee);
}