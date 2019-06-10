package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import EntityHandler.*;

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

    @Override
    public EntityHandler getHandler() {
        return ImportGoodsHandler.getInstance();
    }
}