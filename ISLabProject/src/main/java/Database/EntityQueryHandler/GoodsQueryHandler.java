package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOGoods;

public class GoodsQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOGoods.getInstance();
    }
}
