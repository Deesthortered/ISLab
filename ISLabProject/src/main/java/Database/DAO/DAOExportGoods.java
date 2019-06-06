package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ExportGoods;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOExportGoods extends DAOAbstract {

    private static DAOAbstract instance;

    private DAOExportGoods() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOExportGoods();
        }
        return instance;
    }

    private static final String SQL_GET_QUERY = "SELECT * FROM islabdb.exportgoods " +
            "WHERE (ExportGoods_ID = ?         OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(ExportGoods_DocumentID = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(ExportGoods_GoodsID = ?    OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(ExportGoods_GoodsCount = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(ExportGoods_GoodsPrice = ? OR ? = " + Entity.UNDEFINED_LONG + ")";

    private static final String SQL_ADD_QUERY = "INSERT INTO islabdb.exportgoods (ExportGoods_DocumentID, ExportGoods_GoodsID, ExportGoods_GoodsCount, ExportGoods_GoodsPrice) VALUES (?, ?, ?, ?);";

    private static final String SQL_DELETE_QUERY = "DELETE FROM islabdb.exportgoods " +
            "WHERE (ExportGoods_ID = ?         OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(ExportGoods_DocumentID = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(ExportGoods_GoodsID = ?    OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(ExportGoods_GoodsCount = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(ExportGoods_GoodsPrice = ? OR ? = " + Entity.UNDEFINED_LONG + ");";

    private static final String SQL_IS_EXIST_QUERY = "SELECT COUNT(*) from islabdb.exportgoods where ExportGoods_ID = ?";

    private static final String SQL_EDIT_QUERY = "UPDATE islabdb.exportgoods SET ExportGoods_DocumentID = ?, " +
            "ExportGoods_GoodsID = ?, " +
            "ExportGoods_GoodsCount = ?, " +
            "ExportGoods_GoodsPrice = ? " +
            "WHERE ExportGoods_ID = ?;";

    private static final String SQL_GET_LAST_ID_QUERY = "SELECT max(ExportGoods_ID) FROM islabdb.exportgoods;";


    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws SQLException, ClassNotFoundException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ExportGoods castedFilteringEntity = (ExportGoods) filteringEntity;
        List<Entity> result = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL_GET_QUERY  + ( limited ? " limit ? offset ?" : ""));
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getDocumentId());
        statement.setLong(index++, castedFilteringEntity.getDocumentId());
        statement.setLong(index++, castedFilteringEntity.getGoodsId());
        statement.setLong(index++, castedFilteringEntity.getGoodsId());
        statement.setLong(index++, castedFilteringEntity.getGoodsCount());
        statement.setLong(index++, castedFilteringEntity.getGoodsCount());
        statement.setLong(index++, castedFilteringEntity.getGoodsPrice());
        statement.setLong(index++, castedFilteringEntity.getGoodsPrice());
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

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(List<Entity> list) throws SQLException, ClassNotFoundException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            ExportGoods castedItem = (ExportGoods) item;
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_QUERY);
            int index = 1;
            statement.setLong(index++, castedItem.getDocumentId());
            statement.setLong(index++, castedItem.getGoodsId());
            statement.setLong(index++, castedItem.getGoodsCount());
            statement.setLong(index, castedItem.getGoodsPrice());
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        ExportGoods castedFilter = (ExportGoods) filteringEntity;
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUERY);
        int index = 1;
        statement.setLong(index++, castedFilter.getId());
        statement.setLong(index++, castedFilter.getId());
        statement.setLong(index++, castedFilter.getDocumentId());
        statement.setLong(index++, castedFilter.getDocumentId());
        statement.setLong(index++, castedFilter.getGoodsId());
        statement.setLong(index++, castedFilter.getGoodsId());
        statement.setLong(index++, castedFilter.getGoodsCount());
        statement.setLong(index++, castedFilter.getGoodsCount());
        statement.setLong(index++, castedFilter.getGoodsPrice());
        statement.setLong(index, castedFilter.getGoodsPrice());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement(SQL_IS_EXIST_QUERY);
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        if (!set.next())
            return false;
        int count = set.getInt(1);
        if (count != 1)
            return false;

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean editEntity(Entity editingEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ExportGoods exportGoods = (ExportGoods) editingEntity;

        PreparedStatement statement = connection.prepareStatement(SQL_EDIT_QUERY);
        int index = 1;
        statement.setLong(index++, exportGoods.getDocumentId());
        statement.setLong(index++, exportGoods.getGoodsId());
        statement.setLong(index++, exportGoods.getGoodsCount());
        statement.setLong(index++, exportGoods.getGoodsPrice());
        statement.setLong(index, exportGoods.getId());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        long res = -1;

        PreparedStatement statement = connection.prepareStatement(SQL_GET_LAST_ID_QUERY);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            res = resultSet.getLong(1);
        }

        pool.dropConnection(connection);
        return res;
    }

    @Override
    public Entity createEntity() {
        return new ExportGoods();
    }
}