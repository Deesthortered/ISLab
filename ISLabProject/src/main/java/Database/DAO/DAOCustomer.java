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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Customer casted_filter = (Customer) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.customer " +
                "WHERE (Customer_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Customer_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Customer_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Customer_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getCountry());
        statement.setString(index++, casted_filter.getCountry());
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index++, casted_filter.getDescription());
        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index, start_index);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Customer_ID");
            String name = resultSet.getString("Customer_Name");
            String country = resultSet.getString("Customer_Country");
            String description = resultSet.getString("Customer_Description");
            result.add(new Customer(id, name, country, description));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            Customer casted_item = (Customer) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.customer (Customer_Name, Customer_Country, Customer_Description) VALUES (?, ?, ?);");
            int index = 1;
            statement.setString(index++, casted_item.getName());
            statement.setString(index++, casted_item.getCountry());
            statement.setString(index, casted_item.getDescription());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean DeleteEntityList(Entity filter) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Customer casted_filter = (Customer) filter;

        String sql_query = "DELETE FROM islabdb.customer " +
                "WHERE (Customer_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Customer_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Customer_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Customer_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getCountry());
        statement.setString(index++, casted_filter.getCountry());
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index, casted_filter.getDescription());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean IsExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.customer where Customer_ID = ?");
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        if (!set.next())
            return false;
        int count = set.getInt(1);
        if (count != 1)
            return false;

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean EditEntity(Entity entity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Customer customer = (Customer) entity;

        String sql_code =   "UPDATE islabdb.customer SET Customer_Name = ?, " +
                "Customer_Country = ?, " +
                "Customer_Description = ? " +
                "WHERE Customer_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getCountry());
        statement.setString(3, customer.getDescription());
        statement.setLong(4, customer.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long GetLastID() throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(Customer_ID) FROM islabdb.customer";
        PreparedStatement statement = connection.prepareStatement(sql_code);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            res = resultSet.getLong(1);
        }

        pool.DropConnection(connection);
        return res;
    }

    @Override
    public Entity createEntity() {
        return new Customer();
    }
}
