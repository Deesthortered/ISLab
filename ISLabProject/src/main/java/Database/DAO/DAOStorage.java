package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.Storage;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOStorage extends DAOAbstract {

    private static DAOAbstract instance;

    private DAOStorage() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOStorage();
        }
        return instance;
    }

    private static final String SQL_GET_QUERY = "SELECT * FROM islabdb.storage " +
            "WHERE (Storage_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Storage_Name = ?        OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Storage_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\')";

    private static final String SQL_ADD_QUERY = "INSERT INTO islabdb.storage (Storage_Name, Storage_Description) VALUES (?, ?);";

    private static final String SQL_DELETE_QUERY = "DELETE FROM islabdb.storage " +
            "WHERE (Storage_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Storage_Name = ?        OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Storage_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\');";

    private static final String SQL_IS_EXIST_QUERY = "SELECT COUNT(*) from islabdb.storage where Storage_ID = ?";

    private static final String SQL_EDIT_QUERY = "UPDATE islabdb.storage SET Storage_Name = ?, " +
            "Storage_Description = ? " +
            "WHERE Storage_ID = ?;";

    private static final String SQL_GET_LAST_ID_QUERY = "SELECT max(Storage_ID) FROM islabdb.storage;";


    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Storage castedFilteringEntity = (Storage) filteringEntity;
        List<Entity> result = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL_GET_QUERY + ( limited ? " limit ? offset ?" : ""));
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

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            Storage castedItem = (Storage) item;
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_QUERY);
            int index = 1;
            statement.setString(index++, castedItem.getName());
            statement.setString(index, castedItem.getDescription());
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        Storage castedFilteringEntity = (Storage) filteringEntity;
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUERY);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index, castedFilteringEntity.getDescription());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement(SQL_IS_EXIST_QUERY);
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        if (!set.next())
            return false;
        int count = set.getInt(1);
        if (count != 1)
            return false;

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean editEntity(Entity editingEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Storage storage = (Storage) editingEntity;

        PreparedStatement statement = connection.prepareStatement(SQL_EDIT_QUERY);
        int index = 1;
        statement.setString(index++, storage.getName());
        statement.setString(index++, storage.getDescription());
        statement.setLong(index, storage.getId());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        long res = -1;

        PreparedStatement statement = connection.prepareStatement(SQL_GET_LAST_ID_QUERY);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            res = resultSet.getLong(1);
        }

        pool.dropConnection(connection);
        return res;
    }

    @Override
    public Entity createEntity() {
        return new Storage();
    }
}
