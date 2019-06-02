package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOExportDocument;

public class ExportDocumentQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOExportDocument.getInstance();
    }
}
