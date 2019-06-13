package lab2.EntityQueryHandler;

import lab2.Entity.Entity;
import lab2.Entity.Provider;
import lab2.Repository.EntityRepository;
import lab2.Repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProviderQueryHandler extends EntityQueryHandler {
    private static ProviderQueryHandler instance = new ProviderQueryHandler();
    @Autowired
    private ProviderRepository repository;

    private ProviderQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new Provider();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}