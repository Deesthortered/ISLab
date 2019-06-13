package lab2.Repository;

import lab2.Entity.Provider;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, Long>, EntityRepository<Provider, Long> {
    List<Provider> findAll(Example<Provider> employee);
}