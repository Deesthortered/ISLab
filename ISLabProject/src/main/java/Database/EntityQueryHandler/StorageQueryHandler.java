package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import EntityHandler.*;

public class StorageQueryHandler extends EntityQueryHandler {
    private static StorageQueryHandler instance = new StorageQueryHandler();

    private StorageQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoStorage;
    }

    @Override
    public EntityHandler getHandler() {
        return StorageHandler.getInstance();
    }
}