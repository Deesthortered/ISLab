package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import EntityHandler.*;

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

    @Override
    public EntityHandler getHandler() {
        return GoodsHandler.getInstance();
    }
}