package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import EntityHandler.*;

public class ImportMoveDocumentQueryHandler extends EntityQueryHandler {
    private static ImportMoveDocumentQueryHandler instance = new ImportMoveDocumentQueryHandler();

    private ImportMoveDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoImportMoveDocument;
    }

    @Override
    public EntityHandler getHandler() {
        return ImportMoveDocumentHandler.getInstance();
    }
}