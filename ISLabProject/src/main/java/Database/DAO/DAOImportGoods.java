package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ImportGoods;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ImportGoods castedFilteringEntity = (ImportGoods) filteringEntity;
        List<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.importgoods " +
                "WHERE (ImportGoods_ID = ?         OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(ImportGoods_DocumentID = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(ImportGoods_GoodsID = ?    OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(ImportGoods_GoodsCount = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(ImportGoods_GoodsPrice = ? OR ? = " + Entity.UNDEFINED_LONG + ")" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
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
            long id = resultSet.getLong("ImportGoods_ID");
            long documentId = resultSet.getLong("ImportGoods_DocumentID");
            long goodsId = resultSet.getLong("ImportGoods_GoodsID");
            long goodsCount = resultSet.getLong("ImportGoods_GoodsCount");
            long goodsPrice = resultSet.getLong("ImportGoods_GoodsPrice");
            result.add(new ImportGoods(id, documentId, goodsId, goodsCount, goodsPrice));
        }

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            ImportGoods castedItem = (ImportGoods) item;

            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importgoods (ImportGoods_DocumentID, ImportGoods_GoodsID, ImportGoods_GoodsCount, ImportGoods_GoodsPrice) VALUES (?, ?, ?, ?);");
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

        ImportGoods castedFilteringEntity = (ImportGoods) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.importgoods " +
                "WHERE (ImportGoods_ID = ?         OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(ImportGoods_DocumentID = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(ImportGoods_GoodsID = ?    OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(ImportGoods_GoodsCount = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(ImportGoods_GoodsPrice = ? OR ? = " + Entity.UNDEFINED_LONG + ");";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
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
        statement.setLong(index, castedFilteringEntity.getGoodsPrice());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importgoods where ImportGoods_ID = ?");
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
        ImportGoods importGoods = (ImportGoods) editingEntity;

        String sqlCode =   "UPDATE islabdb.importgoods SET ImportGoods_DocumentID = ?, " +
                "ImportGoods_GoodsID = ?, " +
                "ImportGoods_GoodsCount = ?, " +
                "ImportGoods_GoodsPrice = ? " +
                "WHERE ImportGoods_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
        int index = 1;
        statement.setLong(index++, importGoods.getDocumentId());
        statement.setLong(index++, importGoods.getGoodsId());
        statement.setLong(index++, importGoods.getGoodsCount());
        statement.setLong(index++, importGoods.getGoodsPrice());
        statement.setLong(index, importGoods.getId());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        long res = -1;

        String sql_code = "SELECT max(ImportGoods_ID) FROM islabdb.importgoods;";
        PreparedStatement statement = connection.prepareStatement(sql_code);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            res = resultSet.getLong(1);
        }

        pool.dropConnection(connection);
        return res;
    }

    @Override
    public Entity createEntity() {
        return new ImportGoods();
    }
}
