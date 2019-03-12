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

    public ArrayList<Provider> GetProvidersLIst(Connection connection, int start_index, int count_of_records) {
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
}
