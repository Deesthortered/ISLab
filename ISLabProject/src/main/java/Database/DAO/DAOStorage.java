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
    public ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Storage castedFilteringEntity = (Storage) filteringEntity;
        ArrayList<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.storage " +
                "WHERE (Storage_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Storage_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Storage_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index++, castedFilteringEntity.getDescription());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
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
    public boolean addEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            Storage castedItem = (Storage) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.storage (Storage_Name, Storage_Description) VALUES (?, ?);");
            int index = 1;
            statement.setString(index++, castedItem.getName());
            statement.setString(index, castedItem.getDescription());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        Storage castedFilteringEntity = (Storage) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.storage " +
                "WHERE (Storage_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Storage_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Storage_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index, castedFilteringEntity.getDescription());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
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
    public boolean editEntity(Entity editingEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Storage storage = (Storage) editingEntity;

        String sqlCode =   "UPDATE islabdb.storage SET Storage_Name = ?, " +
                "Storage_Description = ? " +
                "WHERE Storage_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
        int index = 1;
        statement.setString(index++, storage.getName());
        statement.setString(index++, storage.getDescription());
        statement.setLong(index, storage.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sqlCode = "SELECT max(Storage_ID) FROM islabdb.storage;";
        PreparedStatement statement = connection.prepareStatement(sqlCode);
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
