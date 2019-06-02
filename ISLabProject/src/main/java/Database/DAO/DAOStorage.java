package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.Storage;

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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Storage casted_filter = (Storage) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.storage " +
                "WHERE (Storage_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Storage_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Storage_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index++, casted_filter.getDescription());
        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index, start_index);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Storage_ID");
            String name = resultSet.getString("Storage_Name");
            String description = resultSet.getString("Storage_Description");
            result.add(new Storage(id, name, description));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            Storage casted_item = (Storage) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.storage (Storage_Name, Storage_Description) VALUES (?, ?);");
            int index = 1;
            statement.setString(index++, casted_item.getName());
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

        Storage casted_filter = (Storage) filter;
        String sql_query = "DELETE FROM islabdb.storage " +
                "WHERE (Storage_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Storage_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Storage_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getName());
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

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.storage where Storage_ID = ?");
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
        Storage storage = (Storage) entity;

        String sql_code =   "UPDATE islabdb.storage SET Storage_Name = ?, " +
                "Storage_Description = ? " +
                "WHERE Storage_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
        int index = 1;
        statement.setString(index++, storage.getName());
        statement.setString(index++, storage.getDescription());
        statement.setLong(index, storage.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long GetLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(Storage_ID) FROM islabdb.storage;";
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
        return new Storage();
    }
}
