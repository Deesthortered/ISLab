package lab2.EntityQueryHandler;

import lab2.Entity.Customer;
import lab2.Entity.Entity;
import lab2.Repository.CustomerRepository;
import lab2.Repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerQueryHandler extends EntityQueryHandler {
    private static CustomerQueryHandler instance = new CustomerQueryHandler();
    @Autowired
    private CustomerRepository repository;

    private CustomerQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new Customer();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}