package database_package;

import data_model.AvailableGoods;
import data_model.Entity;
import utility_package.Common;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static utility_package.Common.JavaDateToSQLDate;

public class DAOAvailableGoods {

    private static DAOAvailableGoods instance;

    private DAOAvailableGoods() {

    }
    public static synchronized DAOAvailableGoods getInstance() {
        if (instance == null) {
            instance = new DAOAvailableGoods();
        }
        return instance;
    }

    public ArrayList<AvailableGoods> GetAvailableGoodsList(Connection connection, AvailableGoods filter, boolean limited, int start_index, int count_of_records) {
        ArrayList<AvailableGoods> result = new ArrayList<>();

        try {
            String sql_query = "SELECT * FROM islabdb.availablegoods " +
                      "WHERE (Available_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                            "(Available_GoodsID = ?       OR ? = " + Entity.undefined_long + ") AND " +
                            "(Available_ProviderID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                            "(Available_StorageID = ?     OR ? = " + Entity.undefined_long + ") AND " +
                            "(Available_Current = ?       OR ? = false) AND " +
                            "(Available_SnapshotDate = ?  OR ? = \'" + JavaDateToSQLDate(Entity.undefined_date) + "\')" +
                      ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, filter.getId());
            statement.setLong(2, filter.getId());
            statement.setLong(3, filter.getGoods_id());
            statement.setLong(4, filter.getGoods_id());
            statement.setLong(5, filter.getProvider_id());
            statement.setLong(6, filter.getProvider_id());
            statement.setLong(7, filter.getStorage_id());
            statement.setLong(8, filter.getStorage_id());
            statement.setBoolean(9,  filter.isCurrent());
            statement.setBoolean(10, filter.isCurrent());
            statement.setString(11, Common.JavaDateToSQLDate(filter.getSnapshot_date()));
            statement.setString(12, Common.JavaDateToSQLDate(filter.getSnapshot_date()));

            if (limited) {
                statement.setLong(13, count_of_records);
                statement.setLong(14, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id          = resultSet.getLong("Available_ID");
                long goods_id    = resultSet.getLong("Available_GoodsID");
                long provider_id = resultSet.getLong("Available_ProviderID");
                long storage_id  = resultSet.getLong("Available_StorageID");
                boolean current  = resultSet.getBoolean("Available_Current");
                Date snapshot_date = resultSet.getDate("Available_SnapshotDate");
                result.add(new AvailableGoods(id, goods_id, provider_id, storage_id, current, snapshot_date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean AddAvailableGoodsList(Connection connection, ArrayList<AvailableGoods> list) {
        for (AvailableGoods item : list) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO islabdb.availablegoods " +
                        "(Available_ID, Available_GoodsID, Available_ProviderID, Available_StorageID, Available_Current, Available_SnapshotDate) " +
                        "VALUES (?, ?, ?, ?, ?, ?);");
                statement.setLong(1, item.getId());
                statement.setLong(2, item.getGoods_id());
                statement.setLong(3, item.getProvider_id());
                statement.setLong(4, item.getStorage_id());
                statement.setBoolean(5, item.isCurrent());
                statement.setString(6, Common.JavaDateToSQLDate(item.getSnapshot_date()));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
