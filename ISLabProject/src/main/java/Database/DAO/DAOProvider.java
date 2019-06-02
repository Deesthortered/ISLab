package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.Provider;

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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Provider casted_filter = (Provider) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.provider " +
                "WHERE (Provider_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Provider_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Provider_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Provider_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getCountry());
        statement.setString(index++, casted_filter.getCountry());
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index++, casted_filter.getDescription());
        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index++, start_index);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Provider_ID");
            String name = resultSet.getString("Provider_Name");
            String country = resultSet.getString("Provider_Country");
            String description = resultSet.getString("Provider_Description");
            result.add(new Provider(id, name, country, description));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            Provider casted_item = (Provider) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.provider (Provider_Name, Provider_Country, Provider_Description) VALUES (?, ?, ?);");
                int index = 1;
                statement.setString(index++, casted_item.getName());
                statement.setString(index++, casted_item.getCountry());
                statement.setString(index, casted_item.getDescription());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean DeleteEntityList(Entity filter) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        Provider casted_filter = (Provider) filter;
        String sql_query = "DELETE FROM islabdb.provider " +
                "WHERE (Provider_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Provider_Name = ?        OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Provider_Country = ?     OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Provider_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getCountry());
        statement.setString(index++, casted_filter.getCountry());
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

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.provider where Provider_ID = ?");
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
        Provider provider = (Provider) entity;

        String sql_code =   "UPDATE islabdb.provider SET Provider_Name = ?, " +
                "Provider_Country = ?, " +
                "Provider_Description = ? " +
                "WHERE Provider_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
        int index = 1;
        statement.setString(index++, provider.getName());
        statement.setString(index++, provider.getCountry());
        statement.setString(index++, provider.getDescription());
        statement.setLong(index, provider.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long GetLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(Provider_ID) FROM islabdb.provider;";
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
        return new Provider();
    }
}