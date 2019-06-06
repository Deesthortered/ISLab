package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.Provider;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Provider castedFilteringEntity = (Provider) filteringEntity;
        List<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.provider " +
                "WHERE (Provider_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
                "(Provider_Name = ?        OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
                "(Provider_Country = ?     OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
                "(Provider_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
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
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.provider (Provider_Name, Provider_Country, Provider_Description) VALUES (?, ?, ?);");
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
        String sql_query = "DELETE FROM islabdb.provider " +
                "WHERE (Provider_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
                "(Provider_Name = ?        OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
                "(Provider_Country = ?     OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
                "(Provider_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\');";
        PreparedStatement statement = connection.prepareStatement(sql_query);
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

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.provider where Provider_ID = ?");
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

        String sqlCode =   "UPDATE islabdb.provider SET Provider_Name = ?, " +
                "Provider_Country = ?, " +
                "Provider_Description = ? " +
                "WHERE Provider_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
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

        String sqlCode = "SELECT max(Provider_ID) FROM islabdb.provider;";
        PreparedStatement statement = connection.prepareStatement(sqlCode);
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