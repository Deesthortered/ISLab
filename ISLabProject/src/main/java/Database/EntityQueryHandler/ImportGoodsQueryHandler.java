package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOImportGoods;

public class ImportGoodsQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOImportGoods.getInstance();
    }
}
