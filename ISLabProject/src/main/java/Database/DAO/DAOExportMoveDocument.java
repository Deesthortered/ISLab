package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ExportMoveDocument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOExportMoveDocument implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOExportMoveDocument() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOExportMoveDocument();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ExportMoveDocument castedFilteringEntity = (ExportMoveDocument) filteringEntity;
        ArrayList<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.exportmovedocument " +
                "WHERE (Document_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_ExportGoodsID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_StorageID = ?     OR ? = " + Entity.undefined_long + ")" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getExportGoods_id());
        statement.setLong(index++, castedFilteringEntity.getExportGoods_id());
        statement.setLong(index++, castedFilteringEntity.getStorage_id());
        statement.setLong(index++, castedFilteringEntity.getStorage_id());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Document_ID");
            long goodsId = resultSet.getLong("Document_ExportGoodsID");
            long storageId = resultSet.getLong("Document_StorageID");
            result.add(new ExportMoveDocument(id, goodsId, storageId));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ExportMoveDocument castedItem = (ExportMoveDocument) item;

            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportmovedocument (Document_ExportGoodsID, Document_StorageID) VALUES (?, ?);");
            int index = 1;
            statement.setLong(index++, castedItem.getExportGoods_id());
            statement.setLong(index, castedItem.getStorage_id());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ExportMoveDocument castedFilteringEntity = (ExportMoveDocument) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.exportmovedocument " +
                "WHERE (Document_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_ExportGoodsID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_StorageID = ?     OR ? = " + Entity.undefined_long + ");";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getExportGoods_id());
        statement.setLong(index++, castedFilteringEntity.getExportGoods_id());
        statement.setLong(index++, castedFilteringEntity.getStorage_id());
        statement.setLong(index, castedFilteringEntity.getStorage_id());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws SQLException, ClassNotFoundException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.exportmovedocument where Document_ID = ?");
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
        ExportMoveDocument provider = (ExportMoveDocument) editingEntity;

        String sqlCode =   "UPDATE islabdb.exportmovedocument SET Document_ExportGoodsID = ?, " +
                "Document_StorageID = ? " +
                "WHERE Document_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
        int index = 1;
        statement.setLong(index++, provider.getExportGoods_id());
        statement.setLong(index++, provider.getStorage_id());
        statement.setLong(index, provider.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sqlCode = "SELECT max(Document_ID) FROM islabdb.exportmovedocument;";
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
        return new ExportMoveDocument();
    }
}