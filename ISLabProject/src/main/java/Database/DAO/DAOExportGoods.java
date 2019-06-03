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
    public ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ExportGoods castedFilteringEntity = (ExportGoods) filteringEntity;
        ArrayList<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.exportgoods " +
                "WHERE (ExportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ")" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getDocument_id());
        statement.setLong(index++, castedFilteringEntity.getDocument_id());
        statement.setLong(index++, castedFilteringEntity.getGoods_id());
        statement.setLong(index++, castedFilteringEntity.getGoods_id());
        statement.setLong(index++, castedFilteringEntity.getGoods_count());
        statement.setLong(index++, castedFilteringEntity.getGoods_count());
        statement.setLong(index++, castedFilteringEntity.getGoods_price());
        statement.setLong(index++, castedFilteringEntity.getGoods_price());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("ExportGoods_ID");
            long documentId = resultSet.getLong("ExportGoods_DocumentID");
            long goodsId = resultSet.getLong("ExportGoods_GoodsID");
            long goodsCount = resultSet.getLong("ExportGoods_GoodsCount");
            long goodsPrice = resultSet.getLong("ExportGoods_GoodsPrice");
            result.add(new ExportGoods(id, documentId, goodsId, goodsCount, goodsPrice));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(ArrayList<Entity> list) throws SQLException, ClassNotFoundException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ExportGoods castedItem = (ExportGoods) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportgoods (ExportGoods_DocumentID, ExportGoods_GoodsID, ExportGoods_GoodsCount, ExportGoods_GoodsPrice) VALUES (?, ?, ?, ?);");
            int index = 1;
            statement.setLong(index++, castedItem.getDocument_id());
            statement.setLong(index++, castedItem.getGoods_id());
            statement.setLong(index++, castedItem.getGoods_count());
            statement.setLong(index, castedItem.getGoods_price());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ExportGoods castedFilter = (ExportGoods) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.exportgoods " +
                "WHERE (ExportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ExportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ");";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilter.getId());
        statement.setLong(index++, castedFilter.getId());
        statement.setLong(index++, castedFilter.getDocument_id());
        statement.setLong(index++, castedFilter.getDocument_id());
        statement.setLong(index++, castedFilter.getGoods_id());
        statement.setLong(index++, castedFilter.getGoods_id());
        statement.setLong(index++, castedFilter.getGoods_count());
        statement.setLong(index++, castedFilter.getGoods_count());
        statement.setLong(index++, castedFilter.getGoods_price());
        statement.setLong(index, castedFilter.getGoods_price());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
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
    public boolean editEntity(Entity editingEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ExportGoods exportGoods = (ExportGoods) editingEntity;

        String sqlCode =   "UPDATE islabdb.exportgoods SET ExportGoods_DocumentID = ?, " +
                "ExportGoods_GoodsID = ?, " +
                "ExportGoods_GoodsCount = ?, " +
                "ExportGoods_GoodsPrice = ? " +
                "WHERE ExportGoods_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
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
    public long getLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sqlCode = "SELECT max(ExportGoods_ID) FROM islabdb.exportgoods;";
        PreparedStatement statement = connection.prepareStatement(sqlCode);
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