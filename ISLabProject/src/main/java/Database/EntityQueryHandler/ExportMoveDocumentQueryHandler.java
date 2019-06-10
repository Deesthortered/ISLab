package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import EntityHandler.*;

public class ExportMoveDocumentQueryHandler extends EntityQueryHandler {
    private static ExportMoveDocumentQueryHandler instance = new ExportMoveDocumentQueryHandler();

    private ExportMoveDocumentQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoExportMoveDocument;
    }

    @Override
    public EntityHandler getHandler() {
        return ExportMoveDocumentHandler.getInstance();
    }
}