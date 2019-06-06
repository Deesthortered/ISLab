package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOProvider;

public class ProviderQueryHandler extends EntityQueryHandler {
    private static ProviderQueryHandler instance = new ProviderQueryHandler();

    private ProviderQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoProvider;
    }
}