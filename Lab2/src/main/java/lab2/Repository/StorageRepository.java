package lab2.Repository;

import lab2.Entity.Storage;
import org.springframework.data.repository.CrudRepository;

public interface StorageRepository extends CrudRepository<Storage, Long> { }