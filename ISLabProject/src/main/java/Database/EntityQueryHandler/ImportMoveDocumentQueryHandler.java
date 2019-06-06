package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOImportMoveDocument;

public class ImportMoveDocumentQueryHandler extends EntityQueryHandler {
    private static ImportMoveDocumentQueryHandler instance = new ImportMoveDocumentQueryHandler();

    private ImportMoveDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return DAOImportMoveDocument.getInstance();
    }
}
