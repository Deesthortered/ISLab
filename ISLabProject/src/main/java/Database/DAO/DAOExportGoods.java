package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ExportGoods;

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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ExportGoods casted_filter = (ExportGoods) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.exportgoods " +
                "WHERE (ExportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ")" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getDocument_id());
        statement.setLong(index++, casted_filter.getDocument_id());
        statement.setLong(index++, casted_filter.getGoods_id());
        statement.setLong(index++, casted_filter.getGoods_id());
        statement.setLong(index++, casted_filter.getGoods_count());
        statement.setLong(index++, casted_filter.getGoods_count());
        statement.setLong(index++, casted_filter.getGoods_price());
        statement.setLong(index++, casted_filter.getGoods_price());
        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index, start_index);
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

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean AddEntityList(ArrayList<Entity> list) throws SQLException, ClassNotFoundException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ExportGoods casted_item = (ExportGoods) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportgoods (ExportGoods_DocumentID, ExportGoods_GoodsID, ExportGoods_GoodsCount, ExportGoods_GoodsPrice) VALUES (?, ?, ?, ?);");
            int index = 1;
            statement.setLong(index++, casted_item.getDocument_id());
            statement.setLong(index++, casted_item.getGoods_id());
            statement.setLong(index++, casted_item.getGoods_count());
            statement.setLong(index, casted_item.getGoods_price());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean DeleteEntityList(Entity filter) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ExportGoods casted_filter = (ExportGoods) filter;
        String sql_query = "DELETE FROM islabdb.exportgoods " +
                "WHERE (ExportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ");";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getDocument_id());
        statement.setLong(index++, casted_filter.getDocument_id());
        statement.setLong(index++, casted_filter.getGoods_id());
        statement.setLong(index++, casted_filter.getGoods_id());
        statement.setLong(index++, casted_filter.getGoods_count());
        statement.setLong(index++, casted_filter.getGoods_count());
        statement.setLong(index++, casted_filter.getGoods_price());
        statement.setLong(index, casted_filter.getGoods_price());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean IsExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.exportgoods where ExportGoods_ID = ?");
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
        ExportGoods exportGoods = (ExportGoods) entity;

        String sql_code =   "UPDATE islabdb.exportgoods SET ExportGoods_DocumentID = ?, " +
                "ExportGoods_GoodsID = ?, " +
                "ExportGoods_GoodsCount = ?, " +
                "ExportGoods_GoodsPrice = ? " +
                "WHERE ExportGoods_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
        int index = 1;
        statement.setLong(index++, exportGoods.getDocument_id());
        statement.setLong(index++, exportGoods.getGoods_id());
        statement.setLong(index++, exportGoods.getGoods_count());
        statement.setLong(index++, exportGoods.getGoods_price());
        statement.setLong(index, exportGoods.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long GetLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(ExportGoods_ID) FROM islabdb.exportgoods;";
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
        return new ExportGoods();
    }
}