package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOStorage;

public class StorageQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOStorage.getInstance();
    }
}
