package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOImportDocument;

public class ImportDocumentQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOImportDocument.getInstance();
    }
}
