package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOProvider;

public class ProviderQueryHandler extends EntityQueryHandler {

    @Override
    public DAOAbstract getDAO() {
        return DAOProvider.getInstance();
    }
}