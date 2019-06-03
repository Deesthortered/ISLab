package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ImportDocument;
import Utility.DateHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DAOImportDocument implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOImportDocument() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOImportDocument();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ImportDocument castedFilteringEntity = (ImportDocument) filteringEntity;
        ArrayList<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.importdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_ProviderID = ?  OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_ImportDate = ?  OR ? = \'"   + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getProvider_id());
        statement.setLong(index++, castedFilteringEntity.getProvider_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(castedFilteringEntity.getImport_date()));
        statement.setString(index++, DateHandler.JavaDateToSQLDate(castedFilteringEntity.getImport_date()));
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index++, castedFilteringEntity.getDescription());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Document_ID");
            long name = resultSet.getLong("Document_ProviderID");
            Date date = DateHandler.SQLDateToJavaDate(resultSet.getString("Document_ImportDate"));
            String description = resultSet.getString("Document_Description");
            result.add(new ImportDocument(id, name, date, description));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ImportDocument castedItem = (ImportDocument) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importdocument (Document_ProviderID, Document_ImportDate, Document_Description) VALUES (?, ?, ?);");
            int index = 1;
            statement.setLong(index++, castedItem.getProvider_id());
            statement.setString(index++, DateHandler.JavaDateToSQLDate(castedItem.getImport_date()));
            statement.setString(index, castedItem.getDescription());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ImportDocument castedFilteringEntity = (ImportDocument) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.importdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_ProviderID = ?  OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_ImportDate = ?  OR ? = \'"   + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getProvider_id());
        statement.setLong(index++, castedFilteringEntity.getProvider_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(castedFilteringEntity.getImport_date()));
        statement.setString(index++, DateHandler.JavaDateToSQLDate(castedFilteringEntity.getImport_date()));
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index, castedFilteringEntity.getDescription());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importdocument where Document_ID = ?");
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
        ImportDocument document = (ImportDocument) editingEntity;

        String sqlCode =   "UPDATE islabdb.importdocument SET " +
                "Document_ProviderID = ?, " +
                "Document_ImportDate = ?, " +
                "Document_Description = ? " +
                "WHERE Document_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
        int index = 1;
        statement.setLong(index++, document.getProvider_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(document.getImport_date()));
        statement.setString(index++, document.getDescription());
        statement.setLong(index++, document.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sqlCode = "SELECT max(Document_ID) FROM islabdb.importdocument;";
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
        return new ImportDocument();
    }
}