package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import EntityHandler.*;

public class ImportDocumentQueryHandler extends EntityQueryHandler {
    private static ImportDocumentQueryHandler instance = new ImportDocumentQueryHandler();

    private ImportDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoImportDocuments;
    }

    @Override
    public EntityHandler getHandler() {
        return ImportDocumentHandler.getInstance();
    }
}