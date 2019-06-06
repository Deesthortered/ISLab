package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOAvailableGoods;

public class AvailableGoodsQueryHandler extends EntityQueryHandler {
    private static AvailableGoodsQueryHandler instance = new AvailableGoodsQueryHandler();

    private AvailableGoodsQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return DAOAvailableGoods.getInstance();
    }
}
