package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOExportMoveDocument;

public class ExportMoveDocumentQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOExportMoveDocument.getInstance();
    }
}
