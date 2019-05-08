package database_package.dao_package;

import data_model.Entity;
import data_model.ExportGoods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOExportGoods implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOExportGoods() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOExportGoods();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        ExportGoods casted_filter = (ExportGoods) filter;
        ArrayList<Entity> result = new ArrayList<>();
        try {
            String sql_query = "SELECT * FROM islabdb.exportgoods " +
                      "WHERE (ExportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                            "(ExportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                            "(ExportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                            "(ExportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                            "(ExportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ")" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setLong(3, casted_filter.getDocument_id());
            statement.setLong(4, casted_filter.getDocument_id());
            statement.setLong(5, casted_filter.getGoods_id());
            statement.setLong(6, casted_filter.getGoods_id());
            statement.setLong(7, casted_filter.getGoods_count());
            statement.setLong(8, casted_filter.getGoods_count());
            statement.setLong(9, casted_filter.getGoods_price());
            statement.setLong(10, casted_filter.getGoods_price());
            if (limited) {
                statement.setLong(11, count_of_records);
                statement.setLong(12, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("ExportGoods_ID");
                long document_id = resultSet.getLong("ExportGoods_DocumentID");
                long goods_id = resultSet.getLong("ExportGoods_GoodsID");
                long goods_count = resultSet.getLong("ExportGoods_GoodsCount");
                long goods_price = resultSet.getLong("ExportGoods_GoodsPrice");
                result.add(new ExportGoods(id, document_id, goods_id, goods_count, goods_price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            ExportGoods casted_item = (ExportGoods) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportgoods (ExportGoods_DocumentID, ExportGoods_GoodsID, ExportGoods_GoodsCount, ExportGoods_GoodsPrice) VALUES (?, ?, ?, ?);");
                statement.setLong(1, casted_item.getDocument_id());
                statement.setLong(2, casted_item.getGoods_id());
                statement.setLong(3, casted_item.getGoods_count());
                statement.setLong(4, casted_item.getGoods_price());
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
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.exportgoods where ExportGoods_ID = ?");
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM islabdb.exportgoods WHERE ExportGoods_ID = ?;");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean DeleteEntityList(Connection connection, Entity filter) {
        return false;
    }
    @Override
    public boolean EditEntity(Connection connection, Entity entity) {
        ExportGoods exportGoods = (ExportGoods) entity;
        try {
            String sql_code =   "UPDATE islabdb.exportgoods SET ExportGoods_DocumentID = ?, " +
                    "ExportGoods_GoodsID = ?, " +
                    "ExportGoods_GoodsCount = ?, " +
                    "ExportGoods_GoodsPrice = ? " +
                    "WHERE ExportGoods_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setLong(1, exportGoods.getDocument_id());
            statement.setLong(2, exportGoods.getGoods_id());
            statement.setLong(3, exportGoods.getGoods_count());
            statement.setLong(4, exportGoods.getGoods_price());
            statement.setLong(5, exportGoods.getId());
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
            String sql_code = "SELECT max(ExportGoods_ID) FROM islabdb.exportgoods;";
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
        return new ExportGoods();
    }
}