package database_package.dao_package;

import data_model.AvailableGoods;
import data_model.Entity;
import utility_package.DateHandler;

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
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        AvailableGoods casted_filter = (AvailableGoods) filter;
        ArrayList<Entity> result = new ArrayList<>();
        try {
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
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setLong(3, casted_filter.getGoods_id());
            statement.setLong(4, casted_filter.getGoods_id());
            statement.setLong(5, casted_filter.getGoods_count());
            statement.setLong(6, casted_filter.getGoods_count());
            statement.setLong(7, casted_filter.getProvider_id());
            statement.setLong(8, casted_filter.getProvider_id());
            statement.setLong(9, casted_filter.getStorage_id());
            statement.setLong(10, casted_filter.getStorage_id());
            statement.setBoolean(11,  casted_filter.isCurrent());
            statement.setBoolean(12, casted_filter.isCurrent());
            statement.setString(13, DateHandler.JavaDateToSQLDate(casted_filter.getSnapshot_date()));
            statement.setString(14, DateHandler.JavaDateToSQLDate(casted_filter.getSnapshot_date()));

            if (limited) {
                statement.setLong(15, count_of_records);
                statement.setLong(16, start_index);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            AvailableGoods casted_item = (AvailableGoods) item;
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO islabdb.availablegoods " +
                                "(Available_GoodsID, Available_GoodsCount, Available_ProviderID, Available_StorageID, Available_Current, Available_SnapshotDate) " +
                                "VALUES (?, ?, ?, ?, ?, ?);");
                statement.setLong(1, casted_item.getGoods_id());
                statement.setLong(2, casted_item.getGoods_count());
                statement.setLong(3, casted_item.getProvider_id());
                statement.setLong(4, casted_item.getStorage_id());
                statement.setBoolean(5, casted_item.isCurrent());
                statement.setString(6, DateHandler.JavaDateToSQLDate(casted_item.getSnapshot_date()));
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
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setLong(3, casted_filter.getGoods_id());
            statement.setLong(4, casted_filter.getGoods_id());
            statement.setLong(5, casted_filter.getGoods_count());
            statement.setLong(6, casted_filter.getGoods_count());
            statement.setLong(7, casted_filter.getProvider_id());
            statement.setLong(8, casted_filter.getProvider_id());
            statement.setLong(9, casted_filter.getStorage_id());
            statement.setLong(10, casted_filter.getStorage_id());
            statement.setBoolean(11,  casted_filter.isCurrent());
            statement.setBoolean(12, casted_filter.isCurrent());
            statement.setString(13, DateHandler.JavaDateToSQLDate(casted_filter.getSnapshot_date()));
            statement.setString(14, DateHandler.JavaDateToSQLDate(casted_filter.getSnapshot_date()));
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
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.availablegoods where Available_ID = ?");
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
        AvailableGoods document = (AvailableGoods) entity;
        try {
            String sql_code =   "UPDATE islabdb.availablegoods SET " +
                    "Available_GoodsID = ?, " +
                    "Available_GoodsCount = ?, " +
                    "Available_ProviderID = ?, " +
                    "Available_StorageID = ?, " +
                    "Available_Current = ?, " +
                    "Available_SnapshotDate = ? " +
                    "WHERE Available_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setLong(1, document.getGoods_id());
            statement.setLong(2, document.getGoods_count());
            statement.setLong(3, document.getProvider_id());
            statement.setLong(4, document.getStorage_id());
            statement.setBoolean(5, document.isCurrent());
            statement.setString(6, DateHandler.JavaDateToSQLDate(document.getSnapshot_date()));
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
            String sql_code = "SELECT max(Available_ID) FROM islabdb.availablegoods;";
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
        return new AvailableGoods();
    }
}
