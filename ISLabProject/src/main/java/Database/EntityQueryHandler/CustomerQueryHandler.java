package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOCustomer;

public class CustomerQueryHandler extends EntityQueryHandler {
    @Override
    public DAOAbstract getDAO() {
        return DAOCustomer.getInstance();
    }
}
