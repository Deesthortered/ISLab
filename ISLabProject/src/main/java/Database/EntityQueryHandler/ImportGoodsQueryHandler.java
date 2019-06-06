package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOImportGoods;

public class ImportGoodsQueryHandler extends EntityQueryHandler {
    private static ImportGoodsQueryHandler instance = new ImportGoodsQueryHandler();

    private ImportGoodsQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoImportGoods;
    }
}
