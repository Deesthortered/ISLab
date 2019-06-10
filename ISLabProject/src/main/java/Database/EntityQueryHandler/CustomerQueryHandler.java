package Database.EntityQueryHandler;

import Database.DAO.DAOAbstract;
import EntityHandler.*;

public class CustomerQueryHandler extends EntityQueryHandler {
    private static CustomerQueryHandler instance = new CustomerQueryHandler();

    private CustomerQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }
    @Override
    public DAOAbstract getDAO() {
        return this.daoCustomer;
    }

    @Override
    public EntityHandler getHandler() {
        return CustomerHandler.getInstance();
    }
}