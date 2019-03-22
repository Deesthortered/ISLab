package database_package.dao_package;

import data_model.Entity;
import data_model.ImportGoods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOImportGoods implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOImportGoods() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOImportGoods();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        ImportGoods casted_filter = (ImportGoods) filter;
        ArrayList<Entity> result = new ArrayList<>();
        try {
            String sql_query = "SELECT * FROM islabdb.importgoods " +
                      "WHERE (ImportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                            "(ImportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                            "(ImportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                            "(ImportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                            "(ImportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ")" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setLong(1, casted_filter.getDocument_id());
            statement.setLong(2, casted_filter.getDocument_id());
            statement.setLong(1, casted_filter.getGoods_id());
            statement.setLong(2, casted_filter.getGoods_id());
            statement.setLong(1, casted_filter.getGoods_count());
            statement.setLong(2, casted_filter.getGoods_count());
            statement.setLong(1, casted_filter.getGoods_price());
            statement.setLong(2, casted_filter.getGoods_price());
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("ImportGoods_ID");
                long document_id = resultSet.getLong("ImportGoods_DocumentID");
                long goods_id = resultSet.getLong("ImportGoods_GoodsID");
                long goods_count = resultSet.getLong("ImportGoods_GoodsCount");
                long goods_price = resultSet.getLong("ImportGoods_GoodsPrice");
                result.add(new ImportGoods(id, document_id, goods_id, goods_count, goods_price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            ImportGoods casted_item = (ImportGoods) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importgoods (ImportGoods_DocumentID, ImportGoods_GoodsID, ImportGoods_GoodsCount, ImportGoods_GoodsPrice) VALUES (?, ?, ?, ?);");
                statement.setLong(1, casted_item.getDocument_id());
                statement.setLong(1, casted_item.getGoods_id());
                statement.setLong(2, casted_item.getGoods_count());
                statement.setLong(3, casted_item.getGoods_price());
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
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importgoods where ImportGoods_ID = ?");
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM islabdb.importgoods WHERE ImportGoods_ID = ?;");
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
        ImportGoods importGoods = (ImportGoods) entity;
        try {
            String sql_code =   "UPDATE islabdb.importgoods SET ImportGoods_DocumentID = ?, " +
                    "ImportGoods_GoodsID = ?, " +
                    "ImportGoods_GoodsCount = ?, " +
                    "ImportGoods_GoodsPrice = ? " +
                    "WHERE ImportGoods_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setLong(1, importGoods.getDocument_id());
            statement.setLong(2, importGoods.getGoods_id());
            statement.setLong(3, importGoods.getGoods_count());
            statement.setLong(4, importGoods.getGoods_price());
            statement.setLong(5, importGoods.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Entity createEntity() {
        return new ImportGoods();
    }
}
