package database_package.entity_query_handler;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOImportMoveDocument;

public class ImportMoveDocumentQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOImportMoveDocument.getInstance();
    }
}
