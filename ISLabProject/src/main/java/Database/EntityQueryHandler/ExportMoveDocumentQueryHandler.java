package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOExportMoveDocument;

public class ExportMoveDocumentQueryHandler extends EntityQueryHandler {
    private static ExportMoveDocumentQueryHandler instance = new ExportMoveDocumentQueryHandler();

    private ExportMoveDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return DAOExportMoveDocument.getInstance();
    }
}
