package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ImportGoods;

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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ImportGoods casted_filter = (ImportGoods) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.importgoods " +
                "WHERE (ImportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ")" +
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
            statement.setLong(index++, start_index);
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

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ImportGoods casted_item = (ImportGoods) item;

            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importgoods (ImportGoods_DocumentID, ImportGoods_GoodsID, ImportGoods_GoodsCount, ImportGoods_GoodsPrice) VALUES (?, ?, ?, ?);");
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

        ImportGoods casted_filter = (ImportGoods) filter;
        String sql_query = "DELETE FROM islabdb.importgoods " +
                "WHERE (ImportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ");";
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

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importgoods where ImportGoods_ID = ?");
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
        ImportGoods importGoods = (ImportGoods) entity;

        String sql_code =   "UPDATE islabdb.importgoods SET ImportGoods_DocumentID = ?, " +
                "ImportGoods_GoodsID = ?, " +
                "ImportGoods_GoodsCount = ?, " +
                "ImportGoods_GoodsPrice = ? " +
                "WHERE ImportGoods_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
        int index = 1;
        statement.setLong(index++, importGoods.getDocument_id());
        statement.setLong(index++, importGoods.getGoods_id());
        statement.setLong(index++, importGoods.getGoods_count());
        statement.setLong(index++, importGoods.getGoods_price());
        statement.setLong(index, importGoods.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long GetLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(ImportGoods_ID) FROM islabdb.importgoods;";
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
        return new ImportGoods();
    }
}
