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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ImportDocument casted_filter = (ImportDocument) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.importdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_ProviderID = ?  OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_ImportDate = ?  OR ? = \'"   + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getProvider_id());
        statement.setLong(index++, casted_filter.getProvider_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getImport_date()));
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getImport_date()));
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index++, casted_filter.getDescription());
        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index, start_index);
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
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ImportDocument casted_item = (ImportDocument) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importdocument (Document_ProviderID, Document_ImportDate, Document_Description) VALUES (?, ?, ?);");
            int index = 1;
            statement.setLong(index++, casted_item.getProvider_id());
            statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_item.getImport_date()));
            statement.setString(index, casted_item.getDescription());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean DeleteEntityList(Entity filter) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ImportDocument casted_filter = (ImportDocument) filter;
        String sql_query = "DELETE FROM islabdb.importdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_ProviderID = ?  OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_ImportDate = ?  OR ? = \'"   + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getProvider_id());
        statement.setLong(index++, casted_filter.getProvider_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getImport_date()));
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getImport_date()));
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index, casted_filter.getDescription());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean IsExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
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
    public boolean EditEntity(Entity entity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ImportDocument document = (ImportDocument) entity;

        String sql_code =   "UPDATE islabdb.importdocument SET " +
                "Document_ProviderID = ?, " +
                "Document_ImportDate = ?, " +
                "Document_Description = ? " +
                "WHERE Document_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
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
    public long GetLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(Document_ID) FROM islabdb.importdocument;";
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
        return new ImportDocument();
    }
}