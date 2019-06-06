package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ImportMoveDocument;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOImportMoveDocument extends DAOAbstract {

    private static DAOAbstract instance;

    private DAOImportMoveDocument() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOImportMoveDocument();
        }
        return instance;
    }

    private static final String SQL_GET_QUERY = "SELECT * FROM islabdb.importmovedocument " +
            "WHERE (Document_ID = ?            OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_ImportGoodsID = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_StorageID = ?     OR ? = " + Entity.UNDEFINED_LONG + ")";

    private static final String SQL_ADD_QUERY = "INSERT INTO islabdb.importmovedocument (Document_ImportGoodsID, Document_StorageID) VALUES (?, ?);";

    private static final String SQL_DELETE_QUERY = "DELETE FROM islabdb.importmovedocument " +
            "WHERE (Document_ID = ?            OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_ImportGoodsID = ? OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_StorageID = ?     OR ? = " + Entity.UNDEFINED_LONG + ");";

    private static final String SQL_IS_EXIST_QUERY = "SELECT COUNT(*) from islabdb.importmovedocument where Document_ID = ?";

    private static final String SQL_EDIT_QUERY = "UPDATE islabdb.importmovedocument SET Document_ImportGoodsID = ?, " +
            "Document_StorageID = ? " +
            "WHERE Document_ID = ?;";

    private static final String SQL_GET_LAST_ID_QUERY = "SELECT max(Document_ID) FROM islabdb.importmovedocument;";


    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ImportMoveDocument castedFilteringEntity = (ImportMoveDocument) filteringEntity;
        List<Entity> result = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL_GET_QUERY + ( limited ? " limit ? offset ?" : ""));
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getImportGoodsId());
        statement.setLong(index++, castedFilteringEntity.getImportGoodsId());
        statement.setLong(index++, castedFilteringEntity.getStorageId());
        statement.setLong(index++, castedFilteringEntity.getStorageId());
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
    public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            ImportMoveDocument castedItem = (ImportMoveDocument) item;
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_QUERY);
            int index = 1;
            statement.setLong(index++, castedItem.getImportGoodsId());
            statement.setLong(index, castedItem.getStorageId());
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        ImportMoveDocument castedFilteringEntity = (ImportMoveDocument) filteringEntity;
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUERY);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getImportGoodsId());
        statement.setLong(index++, castedFilteringEntity.getImportGoodsId());
        statement.setLong(index++, castedFilteringEntity.getStorageId());
        statement.setLong(index, castedFilteringEntity.getStorageId());
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
        ImportMoveDocument provider = (ImportMoveDocument) editingEntity;

        PreparedStatement statement = connection.prepareStatement(SQL_EDIT_QUERY);
        int index = 1;
        statement.setLong(index++, provider.getImportGoodsId());
        statement.setLong(index++, provider.getStorageId());
        statement.setLong(index, provider.getId());
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
        return new ImportMoveDocument();
    }
}