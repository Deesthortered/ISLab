package database_package.dao_package;

import data_model.Entity;
import data_model.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOStorage implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOStorage() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOStorage();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        Storage casted_filter = (Storage) filter;
        ArrayList<Entity> result = new ArrayList<>();
        try {
            String sql_query = "SELECT * FROM islabdb.storage " +
                      "WHERE (Storage_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                            "(Storage_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                            "(Storage_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setString(3, casted_filter.getName());
            statement.setString(4, casted_filter.getName());
            statement.setString(5, casted_filter.getDescription());
            statement.setString(6, casted_filter.getDescription());
            if (limited) {
                statement.setLong(7, count_of_records);
                statement.setLong(8, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Storage_ID");
                String name = resultSet.getString("Storage_Name");
                String description = resultSet.getString("Storage_Description");
                result.add(new Storage(id, name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            Storage casted_item = (Storage) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.storage (Storage_Name, Storage_Description) VALUES (?, ?);");
                statement.setString(1, casted_item.getName());
                statement.setString(2, casted_item.getDescription());
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
            Storage casted_filter = (Storage) filter;
            String sql_query = "DELETE FROM islabdb.exportmovedocument " +
                    "WHERE (Storage_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                    "(Storage_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                    "(Storage_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setString(3, casted_filter.getName());
            statement.setString(4, casted_filter.getName());
            statement.setString(5, casted_filter.getDescription());
            statement.setString(6, casted_filter.getDescription());
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
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.storage where Storage_ID = ?");
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
        Storage storage = (Storage) entity;
        try {
            String sql_code =   "UPDATE islabdb.storage SET Storage_Name = ?, " +
                    "Storage_Description = ? " +
                    "WHERE Storage_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setString(1, storage.getName());
            statement.setString(2, storage.getDescription());
            statement.setLong(3, storage.getId());
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
            String sql_code = "SELECT max(Storage_ID) FROM islabdb.storage;";
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
        return new Storage();
    }
}
