package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOExportGoods;

public class ExportGoodsQueryHandler extends EntityQueryHandler {
    private static ExportGoodsQueryHandler instance = new ExportGoodsQueryHandler();

    private ExportGoodsQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return DAOExportGoods.getInstance();
    }
}
