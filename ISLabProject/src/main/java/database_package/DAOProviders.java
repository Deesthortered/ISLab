package database_package;

import data_model.Entity;
import data_model.Provider;

import java.sql.*;
import java.util.ArrayList;

public class DAOProviders {

    private static DAOProviders instance;

    private DAOProviders() {

    }
    public static synchronized DAOProviders getInstance() {
        if (instance == null) {
            instance = new DAOProviders();
        }
        return instance;
    }

    public ArrayList<Provider> GetProvidersList(Connection connection, Provider filter, boolean limited, long start_index, long count_of_records) {
        ArrayList<Provider> result = new ArrayList<>();

        try {
            String sql_query = "SELECT * FROM islabdb.provider " +
                      "WHERE (Provider_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                            "(Provider_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                            "(Provider_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                            "(Provider_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                      ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, filter.getId());
            statement.setLong(2, filter.getId());
            statement.setString(3, filter.getName());
            statement.setString(4, filter.getName());
            statement.setString(5, filter.getCountry());
            statement.setString(6, filter.getCountry());
            statement.setString(7, filter.getDescription());
            statement.setString(8, filter.getDescription());
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Provider_ID");
                String name = resultSet.getString("Provider_Name");
                String country = resultSet.getString("Provider_Country");
                String description = resultSet.getString("Provider_Description");
                result.add(new Provider(id, name, country, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean IsExistsProvider(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.provider where Provider_ID = ?");
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
    public boolean AddProvider(Connection connection, Provider provider) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.provider (Provider_Name, Provider_Country, Provider_Description) VALUES (?, ?, ?);");
            statement.setString(1, provider.getName());
            statement.setString(2, provider.getCountry());
            statement.setString(3, provider.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean DeleteProvider(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM islabdb.provider WHERE Provider_ID = ?;");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean EditProvider(Connection connection, Provider provider) {
        try {
            String sql_code =   "UPDATE islabdb.provider SET Provider_Name = ?, " +
                                                            "Provider_Country = ?, " +
                                                            "Provider_Description = ? " +
                                "WHERE Provider_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setString(1, provider.getName());
            statement.setString(2, provider.getCountry());
            statement.setString(3, provider.getDescription());
            statement.setLong(4, provider.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
