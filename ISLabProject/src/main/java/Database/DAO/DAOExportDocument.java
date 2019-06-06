package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ExportDocument;
import Utility.DateHandler;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ExportDocument castedFilteringEntity = (ExportDocument) filteringEntity;
        List<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.exportdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG + ") AND " +
                "(Document_CustomerID = ?  OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Document_ExportDate = ?  OR ? = \'" + DateHandler.javaDateToSQLDate(Entity.UNDEFINED_DATE) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getCustomerId());
        statement.setLong(index++, castedFilteringEntity.getCustomerId());
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getExportDate()));
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getExportDate()));
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
            Date date = DateHandler.sqlDateToJavaDate(resultSet.getString("Document_ExportDate"));
            String description = resultSet.getString("Document_Description");
            result.add(new ExportDocument(id, name, date, description));
        }

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            ExportDocument castedItem = (ExportDocument) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportdocument (Document_CustomerID, Document_ExportDate, Document_Description) VALUES (?, ?, ?);");
            int index = 1;
            statement.setLong(index++, castedItem.getCustomerId());
            statement.setString(index++, DateHandler.javaDateToSQLDate(castedItem.getExportDate()));
            statement.setString(index, castedItem.getDescription());
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws SQLException, ClassNotFoundException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        ExportDocument castedFilteringEntity = (ExportDocument) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.exportdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG + ") AND " +
                "(Document_CustomerID = ?  OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
                "(Document_ExportDate = ?  OR ? = \'" + DateHandler.javaDateToSQLDate(Entity.UNDEFINED_DATE) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\');";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getCustomerId());
        statement.setLong(index++, castedFilteringEntity.getCustomerId());
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getExportDate()));
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getExportDate()));
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index, castedFilteringEntity.getDescription());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws SQLException, ClassNotFoundException, ServletException {
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
    public boolean editEntity(Entity editingEntity) throws SQLException, ClassNotFoundException, ServletException {
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
        statement.setLong(index++, document.getCustomerId());
        statement.setString(index++, DateHandler.javaDateToSQLDate(document.getExportDate()));
        statement.setString(index++, document.getDescription());
        statement.setLong(index, document.getId());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws SQLException, ClassNotFoundException, ServletException {
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