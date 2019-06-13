package lab2.Repository;

import lab2.Entity.Customer;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>, EntityRepository<Customer, Long> {
    List<Customer> findAll(Example<Customer> employee);
}