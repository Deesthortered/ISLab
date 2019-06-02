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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ExportMoveDocument casted_filter = (ExportMoveDocument) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.exportmovedocument " +
                "WHERE (Document_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_ExportGoodsID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_StorageID = ?     OR ? = " + Entity.undefined_long + ")" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getExportGoods_id());
        statement.setLong(index++, casted_filter.getExportGoods_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index, start_index);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Document_ID");
            long goods_id = resultSet.getLong("Document_ExportGoodsID");
            long storage_id = resultSet.getLong("Document_StorageID");
            result.add(new ExportMoveDocument(id, goods_id, storage_id));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ExportMoveDocument casted_item = (ExportMoveDocument) item;

            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportmovedocument (Document_ExportGoodsID, Document_StorageID) VALUES (?, ?);");
            int index = 1;
            statement.setLong(index++, casted_item.getExportGoods_id());
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

        ExportMoveDocument casted_filter = (ExportMoveDocument) filter;
        String sql_query = "DELETE FROM islabdb.exportmovedocument " +
                "WHERE (Document_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_ExportGoodsID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(Document_StorageID = ?     OR ? = " + Entity.undefined_long + ");";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getExportGoods_id());
        statement.setLong(index++, casted_filter.getExportGoods_id());
        statement.setLong(index++, casted_filter.getStorage_id());
        statement.setLong(index, casted_filter.getStorage_id());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean IsExistsEntity(long id) throws SQLException, ClassNotFoundException, InterruptedException {
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
    public boolean EditEntity(Entity entity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ExportMoveDocument provider = (ExportMoveDocument) entity;

        String sql_code =   "UPDATE islabdb.exportmovedocument SET Document_ExportGoodsID = ?, " +
                "Document_StorageID = ? " +
                "WHERE Document_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
        int index = 1;
        statement.setLong(index++, provider.getExportGoods_id());
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

        String sql_code = "SELECT max(Document_ID) FROM islabdb.exportmovedocument;";
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
        return new ExportMoveDocument();
    }
}