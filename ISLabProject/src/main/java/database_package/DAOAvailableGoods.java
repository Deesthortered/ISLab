package database_package;

import data_model.AvailableGoods;

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

    public ArrayList<AvailableGoods> GetAvailableGoodsList(Connection connection, AvailableGoods filter, int start_index, int count_of_records) {
        ArrayList<AvailableGoods> result = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM islabdb.availablegoods " +
                            "WHERE   (Available_ID = ?         OR ? = -1) AND " +
                                    "(Available_GoodsID = ?    OR ? = -1) AND " +
                                    "(Available_ProviderID = ? OR ? = -1) AND " +
                                    "(Available_StorageID = ?  OR ? = -1)" +
                            " limit ? offset ?");

            statement.setLong(1, filter.getId());
            statement.setLong(2, filter.getId());
            statement.setLong(3, filter.getGoods_id());
            statement.setLong(4, filter.getGoods_id());
            statement.setLong(5, filter.getProvider_id());
            statement.setLong(6, filter.getProvider_id());
            statement.setLong(7, filter.getStorage_id());
            statement.setLong(8, filter.getStorage_id());
            statement.setLong(9, count_of_records);
            statement.setLong(10, start_index);
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

}
