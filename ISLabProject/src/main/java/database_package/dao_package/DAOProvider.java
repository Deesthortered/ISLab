package database_package.dao_package;

import data_model.Entity;
import data_model.Provider;

import java.sql.*;
import java.util.ArrayList;

public class DAOProvider implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOProvider() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOProvider();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        Provider casted_filter = (Provider) filter;
        ArrayList<Entity> result = new ArrayList<>();
        try {
            String sql_query = "SELECT * FROM islabdb.provider " +
                    "WHERE (Provider_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                    "(Provider_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                    "(Provider_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                    "(Provider_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
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
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            Provider casted_item = (Provider) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.provider (Provider_Name, Provider_Country, Provider_Description) VALUES (?, ?, ?);");
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
    public boolean DeleteEntityList(Connection connection, Entity filter) {
        try {
            Provider casted_filter = (Provider) filter;
            String sql_query = "DELETE FROM islabdb.provider " +
                    "WHERE (Provider_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                    "(Provider_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                    "(Provider_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                    "(Provider_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setString(3, casted_filter.getName());
            statement.setString(4, casted_filter.getName());
            statement.setString(5, casted_filter.getCountry());
            statement.setString(6, casted_filter.getCountry());
            statement.setString(7, casted_filter.getDescription());
            statement.setString(8, casted_filter.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean IsExistsEntity(Connection connection, long id) {
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
    @Override
    public boolean EditEntity(Connection connection, Entity entity) {
        Provider provider = (Provider) entity;
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

    @Override
    public long GetLastID(Connection connection) {
        long res = -1;
        try {
            String sql_code = "SELECT max(Provider_ID) FROM islabdb.provider;";
            PreparedStatement statement = connection.prepareStatement(sql_code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Entity createEntity() {
        return new Provider();
    }
}