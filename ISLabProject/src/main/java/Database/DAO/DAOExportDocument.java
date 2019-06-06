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

public class DAOExportDocument extends DAOAbstract {

    private static DAOAbstract instance;

    private DAOExportDocument() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOExportDocument();
        }
        return instance;
    }

    private static final String SQL_GET_QUERY = "SELECT * FROM islabdb.exportdocument " +
            "WHERE (Document_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_CustomerID = ?  OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_ExportDate = ?  OR ? = \'" + DateHandler.javaDateToSQLDate(Entity.UNDEFINED_DATE) + "\') AND " +
            "(Document_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\')";

    private static final String SQL_ADD_QUERY = "INSERT INTO islabdb.exportdocument (Document_CustomerID, Document_ExportDate, Document_Description) VALUES (?, ?, ?);";

    private static final String SQL_DELETE_QUERY = "DELETE FROM islabdb.exportdocument " +
            "WHERE (Document_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_CustomerID = ?  OR ? = " + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_ExportDate = ?  OR ? = \'" + DateHandler.javaDateToSQLDate(Entity.UNDEFINED_DATE) + "\') AND " +
            "(Document_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\');";

    private static final String SQL_IS_EXIST_QUERY = "SELECT COUNT(*) from islabdb.exportdocument where Document_ID = ?";

    private static final String SQL_EDIT_QUERY = "UPDATE islabdb.exportdocument SET " +
            "Document_CustomerID = ?, " +
            "Document_ExportDate = ?, " +
            "Document_Description = ? " +
            "WHERE Document_ID = ?;";

    private static final String SQL_GET_LAST_ID_QUERY = "SELECT max(Document_ID) FROM islabdb.exportdocument;";


    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ExportDocument castedFilteringEntity = (ExportDocument) filteringEntity;
        List<Entity> result = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL_GET_QUERY  + ( limited ? " limit ? offset ?" : ""));
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
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_QUERY);
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
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUERY);
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
    public boolean editEntity(Entity editingEntity) throws SQLException, ClassNotFoundException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ExportDocument document = (ExportDocument) editingEntity;

        PreparedStatement statement = connection.prepareStatement(SQL_EDIT_QUERY);
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
        return new ExportDocument();
    }
}