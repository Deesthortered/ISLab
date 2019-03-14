package database_package;

import data_model.Customer;

import java.sql.*;
import java.util.ArrayList;

public class DAOCustomer {

    private static DAOCustomer instance;

    private DAOCustomer() {

    }
    public static synchronized DAOCustomer getInstance() {
        if (instance == null) {
            instance = new DAOCustomer();
        }
        return instance;
    }

    public ArrayList<Customer> GetCustomersList(Connection connection, int start_index, int count_of_records) {
        ArrayList<Customer> result = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM islabdb.customer limit ? offset ?");
            statement.setInt(1, count_of_records);
            statement.setInt(2, start_index);
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
    public Customer GetOneCustomer(Connection connection, long id) {
        Customer customer;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from islabdb.customer where Customer_ID = ?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return new Customer();
            String name = set.getString("Customer_Name");
            String country = set.getString("Customer_Country");
            String description = set.getString("Customer_Description");
            customer = new Customer(id, name, country, description);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Customer();
        }
        return customer;
    }
    public boolean IsExistsCustomer(Connection connection, long id) {
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
    public boolean AddCustomer(Connection connection, Customer customer) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.customer (Customer_Name, Customer_Country, Customer_Description) VALUES (?, ?, ?);");
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getCountry());
            statement.setString(3, customer.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean DeleteCustomer(Connection connection, long id) {
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
    public boolean EditCustomer(Connection connection, Customer customer) {
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
}
