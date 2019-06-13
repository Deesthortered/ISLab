package lab2.EntityQueryHandler;

import lab2.Entity.Entity;
import lab2.Entity.ImportMoveDocument;
import lab2.Repository.EntityRepository;
import lab2.Repository.ImportMoveDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportMoveDocumentQueryHandler extends EntityQueryHandler {
    private static ImportMoveDocumentQueryHandler instance = new ImportMoveDocumentQueryHandler();
    @Autowired
    private ImportMoveDocumentRepository repository;

    private ImportMoveDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new ImportMoveDocument();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}