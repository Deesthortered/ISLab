package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOExportDocument;

public class ExportDocumentQueryHandler extends EntityQueryHandler {
    private static ExportDocumentQueryHandler instance = new ExportDocumentQueryHandler();

    private ExportDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoExportDocument;
    }
}
