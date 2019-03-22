package database_package.entity_query_handler;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOImportDocument;

public class ImportDocumentQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOImportDocument.getInstance();
    }
}
