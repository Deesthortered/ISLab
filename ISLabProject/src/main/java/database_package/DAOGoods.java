package database_package;

import data_model.Entity;
import data_model.Goods;

import java.sql.*;
import java.util.ArrayList;

public class DAOGoods {

    private static DAOGoods instance;

    private DAOGoods() {

    }
    public static synchronized DAOGoods getInstance() {
        if (instance == null) {
            instance = new DAOGoods();
        }
        return instance;
    }

    public ArrayList<Goods> GetGoodsList(Connection connection, Goods filter, boolean limited, int start_index, int count_of_records) {
        ArrayList<Goods> result = new ArrayList<>();

        try {
            String sql_query = "SELECT * FROM islabdb.goods " +
                      "WHERE (Goods_ID = ?           OR ? = "   + Entity.undefined_long   +   ") AND " +
                            "(Goods_Name = ?         OR ? = \'" + Entity.undefined_string + "\') AND " +
                            "(Goods_AveragePrice = ? OR ? = "   + Entity.undefined_long   +   ") AND " +
                            "(Goods_Description = ?  OR ? = \'" + Entity.undefined_string + "\')" +
                      ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, filter.getId());
            statement.setLong(2, filter.getId());
            statement.setString(3, filter.getName());
            statement.setString(4, filter.getName());
            statement.setLong(5, filter.getAverage_price());
            statement.setLong(6, filter.getAverage_price());
            statement.setString(7, filter.getDescription());
            statement.setString(8, filter.getDescription());
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Goods_ID");
                String name = resultSet.getString("Goods_Name");
                long average_price = resultSet.getLong("Goods_AveragePrice");
                String description = resultSet.getString("Goods_Description");
                result.add(new Goods(id, name, average_price, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean IsExistsGoods(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.goods where Goods_ID = ?");
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
    public boolean AddGoods(Connection connection, Goods goods) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.goods (Goods_Name, Goods_AveragePrice, Goods_Description) VALUES (?, ?, ?);");
            statement.setString(1, goods.getName());
            statement.setLong(2, goods.getAverage_price());
            statement.setString(3, goods.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean DeleteGoods(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM islabdb.goods WHERE Goods_ID = ?;");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean EditGoods(Connection connection, Goods goods) {
        try {
            String sql_code =   "UPDATE islabdb.goods SET " +
                    "Goods_Name = ?, " +
                    "Goods_AveragePrice = ?, " +
                    "Goods_Description = ? " +
                    "WHERE Goods_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setString(1, goods.getName());
            statement.setLong(2, goods.getAverage_price());
            statement.setString(3, goods.getDescription());
            statement.setLong(4, goods.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
