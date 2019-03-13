package database_package;

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

    public ArrayList<Provider> GetProvidersList(Connection connection, int start_index, int count_of_records) {
        ArrayList<Provider> result = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM islabdb.provider limit ? offset ?");
            statement.setInt(1, count_of_records);
            statement.setInt(2, start_index);
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
    public Provider GetOneProvider(Connection connection, long id) {
        Provider provider;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from islabdb.provider where Provider_ID = ?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return new Provider();
            String name = set.getString("Provider_Name");
            String country = set.getString("Provider_Country");
            String description = set.getString("Provider_Description");
            provider = new Provider(id, name, country, description);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Provider();
        }
        return provider;
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
