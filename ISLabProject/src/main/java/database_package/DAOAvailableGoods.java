package database_package;

import data_model.AvailableGoods;
import data_model.Entity;

import java.sql.*;
import java.util.ArrayList;

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
                      "WHERE (Available_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                            "(Available_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                            "(Available_ProviderID = ? OR ? = " + Entity.undefined_long + ") AND " +
                            "(Available_StorageID = ?  OR ? = " + Entity.undefined_long + ")" +
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
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id          = resultSet.getLong("Available_ID");
                long goods_id    = resultSet.getLong("Available_GoodsID");
                long provider_id = resultSet.getLong("Available_ProviderID");
                long storage_id  = resultSet.getLong("Available_StorageID");
                result.add(new AvailableGoods(id, goods_id, provider_id, storage_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean AddAvailableGoodsList(Connection connection, ArrayList<AvailableGoods> list) {
        for (AvailableGoods item : list) {
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.availablegoods " +
                        "(Available_ID, Available_GoodsID, Available_ProviderID, Available_StorageID) VALUES (?, ?, ?, ?);");
                statement.setLong(1, item.getId());
                statement.setLong(2, item.getGoods_id());
                statement.setLong(3, item.getProvider_id());
                statement.setLong(3, item.getStorage_id());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
