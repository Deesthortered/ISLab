package lab2.EntityQueryHandler;

import lab2.Entity.Entity;
import lab2.Entity.Storage;
import lab2.Repository.EntityRepository;
import lab2.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StorageQueryHandler extends EntityQueryHandler {
    private static StorageQueryHandler instance = new StorageQueryHandler();
    @Autowired
    private StorageRepository repository;

    private StorageQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new Storage();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}