package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOExportGoods;

public class ExportGoodsQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOExportGoods.getInstance();
    }
}
