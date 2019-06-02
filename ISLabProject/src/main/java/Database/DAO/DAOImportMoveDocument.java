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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ImportMoveDocument casted_filter = (ImportMoveDocument) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.importmovedocument " +
                "WHERE (Document_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_ImportGoodsID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_StorageID = ?     OR ? = " + Entity.undefined_long + ")" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getImportGoods_id());
        statement.setLong(index++, casted_filter.getImportGoods_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index, start_index);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Document_ID");
            long goods_id = resultSet.getLong("Document_ImportGoodsID");
            long storage_id = resultSet.getLong("Document_StorageID");
            result.add(new ImportMoveDocument(id, goods_id, storage_id));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ImportMoveDocument casted_item = (ImportMoveDocument) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importmovedocument (Document_ImportGoodsID, Document_StorageID) VALUES (?, ?);");
            int index = 1;
            statement.setLong(index++, casted_item.getImportGoods_id());
            statement.setLong(index, casted_item.getStorage_id());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean DeleteEntityList(Entity filter) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ImportMoveDocument casted_filter = (ImportMoveDocument) filter;
        String sql_query = "DELETE FROM islabdb.importmovedocument " +
                "WHERE (Document_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_ImportGoodsID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_StorageID = ?     OR ? = " + Entity.undefined_long + ");";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getImportGoods_id());
        statement.setLong(index++, casted_filter.getImportGoods_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        statement.setLong(index, casted_filter.getStorage_id());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean IsExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importmovedocument where Document_ID = ?");
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
        ImportMoveDocument provider = (ImportMoveDocument) entity;

        String sql_code =   "UPDATE islabdb.importmovedocument SET Document_ImportGoodsID = ?, " +
                "Document_StorageID = ? " +
                "WHERE Document_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
        int index = 1;
        statement.setLong(index++, provider.getImportGoods_id());
        statement.setLong(index++, provider.getStorage_id());
        statement.setLong(index, provider.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long GetLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(Document_ID) FROM islabdb.importmovedocument;";
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
        return new ImportMoveDocument();
    }
}