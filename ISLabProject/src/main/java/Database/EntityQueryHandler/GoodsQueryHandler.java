package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOGoods;

public class GoodsQueryHandler extends EntityQueryHandler {
    private static GoodsQueryHandler instance = new GoodsQueryHandler();

    private GoodsQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoGoods;
    }
}
