package lab2.EntityQueryHandler;

import lab2.Entity.Entity;
import lab2.Entity.ExportMoveDocument;
import lab2.Repository.EntityRepository;
import lab2.Repository.ExportMoveDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ExportMoveDocumentQueryHandler extends EntityQueryHandler {
    private static ExportMoveDocumentQueryHandler instance = new ExportMoveDocumentQueryHandler();
    @Autowired
    private ExportMoveDocumentRepository repository;

    private ExportMoveDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new ExportMoveDocument();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}