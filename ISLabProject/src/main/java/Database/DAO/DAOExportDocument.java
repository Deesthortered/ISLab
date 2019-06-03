package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ExportDocument;
import Utility.DateHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DAOExportDocument implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOExportDocument() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOExportDocument();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ExportDocument castedFilteringEntity = (ExportDocument) filteringEntity;
        ArrayList<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.exportdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_CustomerID = ?  OR ? = " + Entity.undefined_long   + ") AND " +
                "(Document_ExportDate = ?  OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getCustomer_id());
        statement.setLong(index++, castedFilteringEntity.getCustomer_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(castedFilteringEntity.getExport_date()));
        statement.setString(index++, DateHandler.JavaDateToSQLDate(castedFilteringEntity.getExport_date()));
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index++, castedFilteringEntity.getDescription());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Document_ID");
            long name = resultSet.getLong("Document_CustomerID");
            Date date = DateHandler.SQLDateToJavaDate(resultSet.getString("Document_ExportDate"));
            String description = resultSet.getString("Document_Description");
            result.add(new ExportDocument(id, name, date, description));
        }

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            ExportDocument castedItem = (ExportDocument) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportdocument (Document_CustomerID, Document_ExportDate, Document_Description) VALUES (?, ?, ?);");
            int index = 1;
            statement.setLong(index++, castedItem.getCustomer_id());
            statement.setString(index++, DateHandler.JavaDateToSQLDate(castedItem.getExport_date()));
            statement.setString(index, castedItem.getDescription());
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        ExportDocument castedFilteringEntity = (ExportDocument) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.exportdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_CustomerID = ?  OR ? = " + Entity.undefined_long   + ") AND " +
                "(Document_ExportDate = ?  OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getCustomer_id());
        statement.setLong(index++, castedFilteringEntity.getCustomer_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(castedFilteringEntity.getExport_date()));
        statement.setString(index++, DateHandler.JavaDateToSQLDate(castedFilteringEntity.getExport_date()));
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index, castedFilteringEntity.getDescription());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.exportdocument where Document_ID = ?");
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
    public boolean editEntity(Entity editingEntity) throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ExportDocument document = (ExportDocument) editingEntity;

        String sqlCode =   "UPDATE islabdb.exportdocument SET " +
                "Document_CustomerID = ?, " +
                "Document_ExportDate = ?, " +
                "Document_Description = ? " +
                "WHERE Document_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
        int index = 1;
        statement.setLong(index++, document.getCustomer_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(document.getExport_date()));
        statement.setString(index++, document.getDescription());
        statement.setLong(index, document.getId());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        long res = -1;

        String sqlCode = "SELECT max(Document_ID) FROM islabdb.exportdocument;";
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
        return new ExportDocument();
    }
}