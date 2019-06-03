package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ImportMoveDocument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOImportMoveDocument implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOImportMoveDocument() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOImportMoveDocument();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ImportMoveDocument castedFilteringEntity = (ImportMoveDocument) filteringEntity;
        ArrayList<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.importmovedocument " +
                "WHERE (Document_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_ImportGoodsID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_StorageID = ?     OR ? = " + Entity.undefined_long + ")" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getImportGoods_id());
        statement.setLong(index++, castedFilteringEntity.getImportGoods_id());
        statement.setLong(index++, castedFilteringEntity.getStorage_id());
        statement.setLong(index++, castedFilteringEntity.getStorage_id());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Document_ID");
            long goodsId = resultSet.getLong("Document_ImportGoodsID");
            long storageId = resultSet.getLong("Document_StorageID");
            result.add(new ImportMoveDocument(id, goodsId, storageId));
        }

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            ImportMoveDocument castedItem = (ImportMoveDocument) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importmovedocument (Document_ImportGoodsID, Document_StorageID) VALUES (?, ?);");
            int index = 1;
            statement.setLong(index++, castedItem.getImportGoods_id());
            statement.setLong(index, castedItem.getStorage_id());
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        ImportMoveDocument castedFilteringEntity = (ImportMoveDocument) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.importmovedocument " +
                "WHERE (Document_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_ImportGoodsID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_StorageID = ?     OR ? = " + Entity.undefined_long + ");";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getImportGoods_id());
        statement.setLong(index++, castedFilteringEntity.getImportGoods_id());
        statement.setLong(index++, castedFilteringEntity.getStorage_id());
        statement.setLong(index, castedFilteringEntity.getStorage_id());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importmovedocument where Document_ID = ?");
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
    public boolean editEntity(Entity editingEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ImportMoveDocument provider = (ImportMoveDocument) editingEntity;

        String sqlCode =   "UPDATE islabdb.importmovedocument SET Document_ImportGoodsID = ?, " +
                "Document_StorageID = ? " +
                "WHERE Document_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
        int index = 1;
        statement.setLong(index++, provider.getImportGoods_id());
        statement.setLong(index++, provider.getStorage_id());
        statement.setLong(index, provider.getId());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        long res = -1;

        String sql_code = "SELECT max(Document_ID) FROM islabdb.importmovedocument;";
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
        return new ImportMoveDocument();
    }
}