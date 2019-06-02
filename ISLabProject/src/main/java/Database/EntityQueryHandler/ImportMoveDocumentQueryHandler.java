package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOImportMoveDocument;

public class ImportMoveDocumentQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOImportMoveDocument.getInstance();
    }
}
