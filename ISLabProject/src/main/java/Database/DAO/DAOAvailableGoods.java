package Database.DAO;

import Database.ConnectionPool;
import Entity.AvailableGoods;
import Entity.Entity;
import Utility.DateHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DAOAvailableGoods implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOAvailableGoods() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOAvailableGoods();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        AvailableGoods casted_filter = (AvailableGoods) filter;
        ArrayList<Entity> result = new ArrayList<>();
        String sql_query = "SELECT * FROM islabdb.availablegoods " +
                "WHERE (Available_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_GoodsID = ?       OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_GoodsCount = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_ProviderID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_StorageID = ?     OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_Current = ?       OR ? = false) AND " +
                "(Available_SnapshotDate = ?  OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getGoods_id());
        statement.setLong(index++, casted_filter.getGoods_id());
        statement.setLong(index++, casted_filter.getGoods_count());
        statement.setLong(index++, casted_filter.getGoods_count());
        statement.setLong(index++, casted_filter.getProvider_id());
        statement.setLong(index++, casted_filter.getProvider_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        statement.setBoolean(index++,  casted_filter.isCurrent());
        statement.setBoolean(index++, casted_filter.isCurrent());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getSnapshot_date()));
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getSnapshot_date()));

        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index, start_index);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id          = resultSet.getLong("Available_ID");
            long goods_id    = resultSet.getLong("Available_GoodsID");
            long goods_count = resultSet.getLong("Available_GoodsCount");
            long provider_id = resultSet.getLong("Available_ProviderID");
            long storage_id  = resultSet.getLong("Available_StorageID");
            boolean current  = resultSet.getBoolean("Available_Current");
            Date snapshot_date = resultSet.getDate("Available_SnapshotDate");
            result.add(new AvailableGoods(id, goods_id, goods_count, provider_id, storage_id, current, snapshot_date));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            AvailableGoods casted_item = (AvailableGoods) item;
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO islabdb.availablegoods " +
                            "(Available_GoodsID, Available_GoodsCount, Available_ProviderID, Available_StorageID, Available_Current, Available_SnapshotDate) " +
                            "VALUES (?, ?, ?, ?, ?, ?);");
            int index = 1;
            statement.setLong(index++, casted_item.getGoods_id());
            statement.setLong(index++, casted_item.getGoods_count());
            statement.setLong(index++, casted_item.getProvider_id());
            statement.setLong(index++, casted_item.getStorage_id());
            statement.setBoolean(index++, casted_item.isCurrent());
            statement.setString(index, DateHandler.JavaDateToSQLDate(casted_item.getSnapshot_date()));
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean DeleteEntityList(Entity filter) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        AvailableGoods casted_filter = (AvailableGoods) filter;
        String sql_query = "DELETE FROM islabdb.availablegoods " +
                "WHERE (Available_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_GoodsID = ?       OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_GoodsCount = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_ProviderID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_StorageID = ?     OR ? = " + Entity.undefined_long + ") AND " +
                "(Available_Current = ?       OR ? = false) AND " +
                "(Available_SnapshotDate = ?  OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\')";
        PreparedStatement statement = connection.prepareStatement(sql_query);

        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getGoods_id());
        statement.setLong(index++, casted_filter.getGoods_id());
        statement.setLong(index++, casted_filter.getGoods_count());
        statement.setLong(index++, casted_filter.getGoods_count());
        statement.setLong(index++, casted_filter.getProvider_id());
        statement.setLong(index++, casted_filter.getProvider_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        statement.setBoolean(index++,  casted_filter.isCurrent());
        statement.setBoolean(index++, casted_filter.isCurrent());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getSnapshot_date()));
        statement.setString(index, DateHandler.JavaDateToSQLDate(casted_filter.getSnapshot_date()));
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean IsExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.availablegoods where Available_ID = ?");
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
    public boolean EditEntity(Entity entity) throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        AvailableGoods document = (AvailableGoods) entity;
        String sql_code =   "UPDATE islabdb.availablegoods SET " +
                "Available_GoodsID = ?, " +
                "Available_GoodsCount = ?, " +
                "Available_ProviderID = ?, " +
                "Available_StorageID = ?, " +
                "Available_Current = ?, " +
                "Available_SnapshotDate = ? " +
                "WHERE Available_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);

        int index = 1;
        statement.setLong(index++, document.getGoods_id());
        statement.setLong(index++, document.getGoods_count());
        statement.setLong(index++, document.getProvider_id());
        statement.setLong(index++, document.getStorage_id());
        statement.setBoolean(index++, document.isCurrent());
        statement.setString(index, DateHandler.JavaDateToSQLDate(document.getSnapshot_date()));
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long GetLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        long res = -1;
        String sql_code = "SELECT max(Available_ID) FROM islabdb.availablegoods;";
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
        return new AvailableGoods();
    }
}
