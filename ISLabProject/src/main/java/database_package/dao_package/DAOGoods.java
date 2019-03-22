package database_package.dao_package;

import data_model.Entity;
import data_model.Goods;

import java.sql.*;
import java.util.ArrayList;

public class DAOGoods implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOGoods() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOGoods();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        Goods casted_filter = (Goods) filter;
        ArrayList<Entity> result = new ArrayList<>();
        try {
            String sql_query = "SELECT * FROM islabdb.goods " +
                    "WHERE (Goods_ID = ?           OR ? = "   + Entity.undefined_long   +   ") AND " +
                    "(Goods_Name = ?         OR ? = \'" + Entity.undefined_string + "\') AND " +
                    "(Goods_AveragePrice = ? OR ? = "   + Entity.undefined_long   +   ") AND " +
                    "(Goods_Description = ?  OR ? = \'" + Entity.undefined_string + "\')" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setString(3, casted_filter.getName());
            statement.setString(4, casted_filter.getName());
            statement.setLong(5, casted_filter.getAverage_price());
            statement.setLong(6, casted_filter.getAverage_price());
            statement.setString(7, casted_filter.getDescription());
            statement.setString(8, casted_filter.getDescription());
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
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            Goods casted_item = (Goods) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.goods (Goods_Name, Goods_AveragePrice, Goods_Description) VALUES (?, ?, ?);");
                statement.setString(1, casted_item.getName());
                statement.setLong(2, casted_item.getAverage_price());
                statement.setString(3, casted_item.getDescription());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean IsExistsEntity(Connection connection, long id) {
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
    @Override
    public boolean DeleteEntity(Connection connection, long id) {
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
    @Override
    public boolean EditEntity(Connection connection, Entity entity) {
        Goods goods = (Goods) entity;
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

    @Override
    public Entity createEntity() {
        return new Goods();
    }
}
