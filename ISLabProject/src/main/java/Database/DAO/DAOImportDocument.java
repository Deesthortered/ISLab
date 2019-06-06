package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ImportDocument;
import Utility.DateHandler;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOImportDocument extends DAOAbstract {

    private static DAOAbstract instance;

    private DAOImportDocument() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOImportDocument();
        }
        return instance;
    }

    private static final String SQL_GET_QUERY = "SELECT * FROM islabdb.importdocument " +
            "WHERE (Document_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_ProviderID = ?  OR ? = "   + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_ImportDate = ?  OR ? = \'"   + DateHandler.javaDateToSQLDate(Entity.UNDEFINED_DATE) + "\') AND " +
            "(Document_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\')";

    private static final String SQL_ADD_QUERY = "INSERT INTO islabdb.importdocument (Document_ProviderID, Document_ImportDate, Document_Description) VALUES (?, ?, ?);";

    private static final String SQL_DELETE_QUERY = "DELETE FROM islabdb.importdocument " +
            "WHERE (Document_ID = ?          OR ? = "   + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_ProviderID = ?  OR ? = "   + Entity.UNDEFINED_LONG + ") AND " +
            "(Document_ImportDate = ?  OR ? = \'"   + DateHandler.javaDateToSQLDate(Entity.UNDEFINED_DATE) + "\') AND " +
            "(Document_Description = ? OR ? = \'" + Entity.UNDEFINED_STRING + "\');";

    private static final String SQL_IS_EXIST_QUERY = "SELECT COUNT(*) from islabdb.importdocument where Document_ID = ?";

    private static final String SQL_EDIT_QUERY = "UPDATE islabdb.importdocument SET " +
            "Document_ProviderID = ?, " +
            "Document_ImportDate = ?, " +
            "Document_Description = ? " +
            "WHERE Document_ID = ?;";

    private static final String SQL_GET_LAST_ID_QUERY = "SELECT max(Document_ID) FROM islabdb.importdocument;";

    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ImportDocument castedFilteringEntity = (ImportDocument) filteringEntity;
        List<Entity> result = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL_GET_QUERY + ( limited ? " limit ? offset ?" : ""));
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getProviderId());
        statement.setLong(index++, castedFilteringEntity.getProviderId());
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getImportDate()));
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getImportDate()));
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
            Date date = DateHandler.sqlDateToJavaDate(resultSet.getString("Document_ImportDate"));
            String description = resultSet.getString("Document_Description");
            result.add(new ImportDocument(id, name, date, description));
        }

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            ImportDocument castedItem = (ImportDocument) item;
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_QUERY);
            int index = 1;
            statement.setLong(index++, castedItem.getProviderId());
            statement.setString(index++, DateHandler.javaDateToSQLDate(castedItem.getImportDate()));
            statement.setString(index, castedItem.getDescription());
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        ImportDocument castedFilteringEntity = (ImportDocument) filteringEntity;
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUERY);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getProviderId());
        statement.setLong(index++, castedFilteringEntity.getProviderId());
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getImportDate()));
        statement.setString(index++, DateHandler.javaDateToSQLDate(castedFilteringEntity.getImportDate()));
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index, castedFilteringEntity.getDescription());
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
        ImportDocument document = (ImportDocument) editingEntity;

        PreparedStatement statement = connection.prepareStatement(SQL_EDIT_QUERY);
        int index = 1;
        statement.setLong(index++, document.getProviderId());
        statement.setString(index++, DateHandler.javaDateToSQLDate(document.getImportDate()));
        statement.setString(index++, document.getDescription());
        statement.setLong(index++, document.getId());
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
        return new ImportDocument();
    }
}