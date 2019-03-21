package database_package;

import data_model.Entity;
import data_model.ImportMoveDocument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOImportMoveDocument implements DAOInterface {

    private static DAOInterface instance;

    private DAOImportMoveDocument() {

    }
    public static synchronized DAOInterface getInstance() {
        if (instance == null) {
            instance = new DAOImportMoveDocument();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        ImportMoveDocument casted_filter = (ImportMoveDocument) filter;
        ArrayList<Entity> result = new ArrayList<>();
        try {
            String sql_query = "SELECT * FROM islabdb.importmovedocument " +
                      "WHERE (Document_ID = ?            OR ? = " + Entity.undefined_long + ") AND " +
                            "(Document_ImportGoodsID = ? OR ? = " + Entity.undefined_long + ") AND " +
                            "(Document_StorageID = ?     OR ? = " + Entity.undefined_long + ")" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setLong(1, casted_filter.getImportGoods_id());
            statement.setLong(2, casted_filter.getImportGoods_id());
            statement.setLong(1, casted_filter.getStorage_id());
            statement.setLong(2, casted_filter.getStorage_id());
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Document_ID");
                long goods_id = resultSet.getLong("Document_ImportGoodsID");
                long storage_id = resultSet.getLong("Document_StorageID");
                result.add(new ImportMoveDocument(id, goods_id, storage_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            ImportMoveDocument casted_item = (ImportMoveDocument) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importmovedocument (Document_ImportGoodsID, Document_StorageID) VALUES (?, ?);");
                statement.setLong(1, casted_item.getImportGoods_id());
                statement.setLong(2, casted_item.getStorage_id());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean IsExistsEntity(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importmovedocument where Document_ID = ?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return false;
            int count = set.getInt(1);
            if (count != 1)
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean DeleteEntity(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM islabdb.importmovedocument WHERE Document_ID = ?;");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean EditEntity(Connection connection, Entity entity) {
        ImportMoveDocument provider = (ImportMoveDocument) entity;
        try {
            String sql_code =   "UPDATE islabdb.importmovedocument SET Document_ImportGoodsID = ?, " +
                    "Document_StorageID = ? " +
                    "WHERE Document_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setLong(1, provider.getImportGoods_id());
            statement.setLong(2, provider.getStorage_id());
            statement.setLong(3, provider.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}