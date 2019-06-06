package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.Provider;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOProvider extends DAOAbstract {

    private static DAOAbstract instance;

    private DAOProvider() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOProvider();
        }
        return instance;
    }

    private static final String SQL_GET_QUERY = "SELECT * FROM islabdb.provider " +
            "WHERE (Provider_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Provider_Name = ?        OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Provider_Country = ?     OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Provider_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\')";

    private static final String SQL_ADD_QUERY = "INSERT INTO islabdb.provider (Provider_Name, Provider_Country, Provider_Description) VALUES (?, ?, ?);";

    private static final String SQL_DELETE_QUERY = "DELETE FROM islabdb.provider " +
            "WHERE (Provider_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Provider_Name = ?        OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Provider_Country = ?     OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Provider_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\');";

    private static final String SQL_IS_EXIST_QUERY = "SELECT COUNT(*) from islabdb.provider where Provider_ID = ?";

    private static final String SQL_EDIT_QUERY = "UPDATE islabdb.provider SET Provider_Name = ?, " +
            "Provider_Country = ?, " +
            "Provider_Description = ? " +
            "WHERE Provider_ID = ?;";

    private static final String SQL_GET_LAST_ID_QUERY = "SELECT max(Provider_ID) FROM islabdb.provider;";


    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Provider castedFilteringEntity = (Provider) filteringEntity;
        List<Entity> result = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL_GET_QUERY + ( limited ? " limit ? offset ?" : ""));
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getCountry());
        statement.setString(index++, castedFilteringEntity.getCountry());
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index++, castedFilteringEntity.getDescription());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Provider_ID");
            String name = resultSet.getString("Provider_Name");
            String country = resultSet.getString("Provider_Country");
            String description = resultSet.getString("Provider_Description");
            result.add(new Provider(id, name, country, description));
        }

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            Provider castedItem = (Provider) item;
            try {
                PreparedStatement statement = connection.prepareStatement(SQL_ADD_QUERY);
                int index = 1;
                statement.setString(index++, castedItem.getName());
                statement.setString(index++, castedItem.getCountry());
                statement.setString(index, castedItem.getDescription());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        Provider castedFilter = (Provider) filteringEntity;
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUERY);
        int index = 1;
        statement.setLong(index++, castedFilter.getId());
        statement.setLong(index++, castedFilter.getId());
        statement.setString(index++, castedFilter.getName());
        statement.setString(index++, castedFilter.getName());
        statement.setString(index++, castedFilter.getCountry());
        statement.setString(index++, castedFilter.getCountry());
        statement.setString(index++, castedFilter.getDescription());
        statement.setString(index, castedFilter.getDescription());
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
        Provider provider = (Provider) editingEntity;

        PreparedStatement statement = connection.prepareStatement(SQL_EDIT_QUERY);
        int index = 1;
        statement.setString(index++, provider.getName());
        statement.setString(index++, provider.getCountry());
        statement.setString(index++, provider.getDescription());
        statement.setLong(index, provider.getId());
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
        return new Provider();
    }
}