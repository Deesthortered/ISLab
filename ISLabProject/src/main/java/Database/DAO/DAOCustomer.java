package Database.DAO;

import Database.ConnectionPool;
import Entity.Customer;
import Entity.Entity;

import java.sql.*;
import java.util.ArrayList;

public class DAOCustomer implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOCustomer() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOCustomer();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Customer castedFilteringEntity = (Customer) filteringEntity;
        ArrayList<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.customer " +
                "WHERE (Customer_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Customer_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Customer_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Customer_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
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
    public boolean addEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            Customer castedItem = (Customer) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.customer (Customer_Name, Customer_Country, Customer_Description) VALUES (?, ?, ?);");
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
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Customer castedFilteringEntity = (Customer) filteringEntity;

        String sqlQuery = "DELETE FROM islabdb.customer " +
                "WHERE (Customer_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Customer_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Customer_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Customer_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
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
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.customer where Customer_ID = ?");
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
    public boolean editEntity(Entity editingEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Customer customer = (Customer) editingEntity;

        String sqlCode =   "UPDATE islabdb.customer SET Customer_Name = ?, " +
                "Customer_Country = ?, " +
                "Customer_Description = ? " +
                "WHERE Customer_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getCountry());
        statement.setString(3, customer.getDescription());
        statement.setLong(4, customer.getId());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        long res = -1;

        String sqlCode = "SELECT max(Customer_ID) FROM islabdb.customer";
        PreparedStatement statement = connection.prepareStatement(sqlCode);
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
