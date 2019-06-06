package Database.DAO;

import Database.ConnectionPool;
import Entity.AvailableGoods;
import Entity.Entity;
import Utility.DateHandler;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOAvailableGoods implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOAvailableGoods() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOAvailableGoods();
        }
        return instance;
    }

    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        AvailableGoods castedFilteringEntity = (AvailableGoods) filteringEntity;
        List<Entity> result = new ArrayList<>();
        String sqlGetQuery = "SELECT * FROM islabdb.availablegoods " +
                "WHERE (Available_ID = ?            OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_GoodsID = ?       OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_GoodsCount = ?    OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_ProviderID = ?    OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_StorageID = ?     OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_Current = ?       OR ? = false) AND " +
                "(Available_SnapshotDate = ?  OR ? = \'" + DateHandler.javaDateToSQLDate(Entity.UNDEFINED_DATE) + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlGetQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getGoodsId());
        statement.setLong(index++, castedFilteringEntity.getGoodsId());
        statement.setLong(index++, castedFilteringEntity.getGoodsCount());
        statement.setLong(index++, castedFilteringEntity.getGoodsCount());
        statement.setLong(index++, castedFilteringEntity.getProviderId());
        statement.setLong(index++, castedFilteringEntity.getProviderId());
        statement.setLong(index++, castedFilteringEntity.getStorageId());
        statement.setLong(index++, castedFilteringEntity.getStorageId());
        statement.setBoolean(index++,  castedFilteringEntity.isCurrent());
        statement.setBoolean(index++, castedFilteringEntity.isCurrent());
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getSnapshotDate()));
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getSnapshotDate()));

        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id          = resultSet.getLong("Available_ID");
            long goodsId    = resultSet.getLong("Available_GoodsID");
            long goodsCount = resultSet.getLong("Available_GoodsCount");
            long providerId = resultSet.getLong("Available_ProviderID");
            long storageId  = resultSet.getLong("Available_StorageID");
            boolean current  = resultSet.getBoolean("Available_Current");
            Date snapshotDate = resultSet.getDate("Available_SnapshotDate");
            result.add(new AvailableGoods(id, goodsId, goodsCount, providerId, storageId, current, snapshotDate));
        }

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            AvailableGoods castedItem = (AvailableGoods) item;
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO islabdb.availablegoods " +
                            "(Available_GoodsID, Available_GoodsCount, Available_ProviderID, Available_StorageID, Available_Current, Available_SnapshotDate) " +
                            "VALUES (?, ?, ?, ?, ?, ?);");
            int index = 1;
            statement.setLong(index++, castedItem.getGoodsId());
            statement.setLong(index++, castedItem.getGoodsCount());
            statement.setLong(index++, castedItem.getProviderId());
            statement.setLong(index++, castedItem.getStorageId());
            statement.setBoolean(index++, castedItem.isCurrent());
            statement.setString(index, DateHandler.javaDateToSQLDate(castedItem.getSnapshotDate()));
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        AvailableGoods castedFilteringEntity = (AvailableGoods) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.availablegoods " +
                "WHERE (Available_ID = ?            OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_GoodsID = ?       OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_GoodsCount = ?    OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_ProviderID = ?    OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_StorageID = ?     OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Available_Current = ?       OR ? = false) AND " +
                "(Available_SnapshotDate = ?  OR ? = \'" + DateHandler.javaDateToSQLDate(Entity.UNDEFINED_DATE) + "\')";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);

        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getGoodsId());
        statement.setLong(index++, castedFilteringEntity.getGoodsId());
        statement.setLong(index++, castedFilteringEntity.getGoodsCount());
        statement.setLong(index++, castedFilteringEntity.getGoodsCount());
        statement.setLong(index++, castedFilteringEntity.getProviderId());
        statement.setLong(index++, castedFilteringEntity.getProviderId());
        statement.setLong(index++, castedFilteringEntity.getStorageId());
        statement.setLong(index++, castedFilteringEntity.getStorageId());
        statement.setBoolean(index++,  castedFilteringEntity.isCurrent());
        statement.setBoolean(index++, castedFilteringEntity.isCurrent());
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getSnapshotDate()));
        statement.setString(index, DateHandler.javaDateToSQLDate(castedFilteringEntity.getSnapshotDate()));
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.availablegoods where Available_ID = ?");
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
    public boolean editEntity(Entity editingEntity) throws SQLException, ClassNotFoundException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        AvailableGoods document = (AvailableGoods) editingEntity;
        String sqlCode =   "UPDATE islabdb.availablegoods SET " +
                "Available_GoodsID = ?, " +
                "Available_GoodsCount = ?, " +
                "Available_ProviderID = ?, " +
                "Available_StorageID = ?, " +
                "Available_Current = ?, " +
                "Available_SnapshotDate = ? " +
                "WHERE Available_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);

        int index = 1;
        statement.setLong(index++, document.getGoodsId());
        statement.setLong(index++, document.getGoodsCount());
        statement.setLong(index++, document.getProviderId());
        statement.setLong(index++, document.getStorageId());
        statement.setBoolean(index++, document.isCurrent());
        statement.setString(index, DateHandler.javaDateToSQLDate(document.getSnapshotDate()));
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        long res = -1;
        String sqlCode = "SELECT max(Available_ID) FROM islabdb.availablegoods;";
        PreparedStatement statement = connection.prepareStatement(sqlCode);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            res = resultSet.getLong(1);
        }
        pool.dropConnection(connection);
        return res;
    }

    @Override
    public Entity createEntity() {
        return new AvailableGoods();
    }
}