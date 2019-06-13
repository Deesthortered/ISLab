package lab2.EntityQueryHandler;

import lab2.Entity.Entity;
import lab2.Entity.ExportDocument;
import lab2.Repository.EntityRepository;
import lab2.Repository.ExportDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ExportDocumentQueryHandler extends EntityQueryHandler {
    private static ExportDocumentQueryHandler instance = new ExportDocumentQueryHandler();
    @Autowired
    private ExportDocumentRepository repository;

    private ExportDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new ExportDocument();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}