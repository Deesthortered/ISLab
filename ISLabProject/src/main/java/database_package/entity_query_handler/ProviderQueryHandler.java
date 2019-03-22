package database_package.entity_query_handler;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOProvider;

public class ProviderQueryHandler extends EntityQueryHandler {

    @Override
    public DAOAbstract getDAO() {
        return DAOProvider.getInstance();
    }
}