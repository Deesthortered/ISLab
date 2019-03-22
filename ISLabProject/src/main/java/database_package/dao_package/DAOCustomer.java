package database_package.dao_package;

import data_model.Customer;
import data_model.Entity;

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
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        Customer casted_filter = (Customer) filter;
        ArrayList<Entity> result = new ArrayList<>();
        try {
            String sql_query = "SELECT * FROM islabdb.customer " +
                    "WHERE (Customer_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                    "(Customer_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                    "(Customer_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                    "(Customer_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setString(3, casted_filter.getName());
            statement.setString(4, casted_filter.getName());
            statement.setString(5, casted_filter.getCountry());
            statement.setString(6, casted_filter.getCountry());
            statement.setString(7, casted_filter.getDescription());
            statement.setString(8, casted_filter.getDescription());
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Customer_ID");
                String name = resultSet.getString("Customer_Name");
                String country = resultSet.getString("Customer_Country");
                String description = resultSet.getString("Customer_Description");
                result.add(new Customer(id, name, country, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            Customer casted_item = (Customer) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.customer (Customer_Name, Customer_Country, Customer_Description) VALUES (?, ?, ?);");
                statement.setString(1, casted_item.getName());
                statement.setString(2, casted_item.getCountry());
                statement.setString(3, casted_item.getDescription());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean IsExistsEntity(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.customer where Customer_ID = ?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return false;
            int count = set.getInt(1);
            if (count != 1)
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean DeleteEntity(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM islabdb.customer WHERE Customer_ID = ?;");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean EditEntity(Connection connection, Entity entity) {
        Customer customer = (Customer) entity;
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Entity createEntity() {
        return new Customer();
    }
}
