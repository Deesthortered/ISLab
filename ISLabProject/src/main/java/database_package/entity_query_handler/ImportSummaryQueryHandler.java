package database_package.entity_query_handler;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOImportSummary;

public class ImportSummaryQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOImportSummary.getInstance();
    }
}
