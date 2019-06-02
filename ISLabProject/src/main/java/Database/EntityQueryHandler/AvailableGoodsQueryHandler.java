package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOAvailableGoods;

public class AvailableGoodsQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOAvailableGoods.getInstance();
    }
}
