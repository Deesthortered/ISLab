package database_package.entity_query_handler;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOExportMoveDocument;

public class ExportMoveDocumentQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOExportMoveDocument.getInstance();
    }
}
