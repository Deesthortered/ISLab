package Database.DAO;

import Database.ConnectionPool;
import Entity.Customer;
import Entity.Entity;
import Utility.DateHandler;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCustomer extends DAOAbstract {

    private static DAOAbstract instance;

    private DAOCustomer() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOCustomer();
        }
        return instance;
    }

    private static final String SQL_GET_QUERY = "SELECT * FROM islabdb.customer " +
            "WHERE (Customer_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Customer_Name = ?        OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Customer_Country = ?     OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Customer_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\')";

    private static final String SQL_ADD_QUERY = "INSERT INTO islabdb.customer (Customer_Name, Customer_Country, Customer_Description) VALUES (?, ?, ?);";

    private static final String SQL_DELETE_QUERY = "DELETE FROM islabdb.customer " +
            "WHERE (Customer_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Customer_Name = ?        OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Customer_Country = ?     OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Customer_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\');";

    private static final String SQL_IS_EXIST_QUERY = "SELECT COUNT(*) from islabdb.customer where Customer_ID = ?";

    private static final String SQL_EDIT_QUERY = "UPDATE islabdb.customer SET Customer_Name = ?, " +
            "Customer_Country = ?, " +
            "Customer_Description = ? " +
            "WHERE Customer_ID = ?;";

    private static final String SQL_GET_LAST_ID_QUERY = "SELECT max(Customer_ID) FROM islabdb.customer";

    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Customer castedFilteringEntity = (Customer) filteringEntity;
        List<Entity> result = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL_GET_QUERY + ( limited ? " limit ? offset ?" : ""));
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getCountry());
        statement.setString(index++, castedFilteringEntity.getCountry());
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index++, castedFilteringEntity.getDescription());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Customer_ID");
            String name = resultSet.getString("Customer_Name");
            String country = resultSet.getString("Customer_Country");
            String description = resultSet.getString("Customer_Description");
            result.add(new Customer(id, name, country, description));
        }

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            Customer castedItem = (Customer) item;
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_QUERY);
            int index = 1;
            statement.setString(index++, castedItem.getName());
            statement.setString(index++, castedItem.getCountry());
            statement.setString(index, castedItem.getDescription());
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Customer castedFilteringEntity = (Customer) filteringEntity;

        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUERY);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getCountry());
        statement.setString(index++, castedFilteringEntity.getCountry());
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index, castedFilteringEntity.getDescription());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement(SQL_IS_EXIST_QUERY);
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        if (!set.next())
            return false;
        int count = set.getInt(1);
        if (count != 1)
            return false;

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean editEntity(Entity editingEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Customer customer = (Customer) editingEntity;

        PreparedStatement statement = connection.prepareStatement(SQL_EDIT_QUERY);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getCountry());
        statement.setString(3, customer.getDescription());
        statement.setLong(4, customer.getId());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws SQLException, ClassNotFoundException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        long res = -1;

        PreparedStatement statement = connection.prepareStatement(SQL_GET_LAST_ID_QUERY);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            res = resultSet.getLong(1);
        }

        pool.dropConnection(connection);
        return res;
    }

    @Override
    public Entity createEntity() {
        return new Customer();
    }
}
