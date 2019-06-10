package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import EntityHandler.*;

public class ExportGoodsQueryHandler extends EntityQueryHandler {
    private static ExportGoodsQueryHandler instance = new ExportGoodsQueryHandler();

    private ExportGoodsQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoExportGoods;
    }

    @Override
    public EntityHandler getHandler() {
        return ExportGoodsHandler.getInstance();
    }
}