package lab2.EntityQueryHandler;

import lab2.Entity.Entity;
import lab2.Entity.ImportDocument;
import lab2.Repository.EntityRepository;
import lab2.Repository.ImportDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportDocumentQueryHandler extends EntityQueryHandler {
    private static ImportDocumentQueryHandler instance = new ImportDocumentQueryHandler();
    @Autowired
    private ImportDocumentRepository repository;

    private ImportDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new ImportDocument();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}