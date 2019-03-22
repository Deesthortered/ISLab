package database_package.entity_query_handler;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOAvailableGoods;

public class AvailableGoodsQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOAvailableGoods.getInstance();
    }
}
