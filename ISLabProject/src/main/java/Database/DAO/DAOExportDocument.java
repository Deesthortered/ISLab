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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ExportDocument casted_filter = (ExportDocument) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.exportdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_CustomerID = ?  OR ? = " + Entity.undefined_long   + ") AND " +
                "(Document_ExportDate = ?  OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getCustomer_id());
        statement.setLong(index++, casted_filter.getCustomer_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getExport_date()));
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getExport_date()));
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index++, casted_filter.getDescription());
        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index, start_index);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Document_ID");
            long name = resultSet.getLong("Document_CustomerID");
            Date date = DateHandler.SQLDateToJavaDate(resultSet.getString("Document_ExportDate"));
            String description = resultSet.getString("Document_Description");
            result.add(new ExportDocument(id, name, date, description));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ExportDocument casted_item = (ExportDocument) item;
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportdocument (Document_CustomerID, Document_ExportDate, Document_Description) VALUES (?, ?, ?);");
            int index = 1;
            statement.setLong(index++, casted_item.getCustomer_id());
            statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_item.getExport_date()));
            statement.setString(index, casted_item.getDescription());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean DeleteEntityList(Entity filter) throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ExportDocument casted_filter = (ExportDocument) filter;
        String sql_query = "DELETE FROM islabdb.exportdocument " +
                "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                "(Document_CustomerID = ?  OR ? = " + Entity.undefined_long   + ") AND " +
                "(Document_ExportDate = ?  OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getCustomer_id());
        statement.setLong(index++, casted_filter.getCustomer_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getExport_date()));
        statement.setString(index++, DateHandler.JavaDateToSQLDate(casted_filter.getExport_date()));
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index, casted_filter.getDescription());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean IsExistsEntity(long id) throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.exportdocument where Document_ID = ?");
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
    public boolean EditEntity(Entity entity) throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ExportDocument document = (ExportDocument) entity;

        String sql_code =   "UPDATE islabdb.exportdocument SET " +
                "Document_CustomerID = ?, " +
                "Document_ExportDate = ?, " +
                "Document_Description = ? " +
                "WHERE Document_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
        int index = 1;
        statement.setLong(index++, document.getCustomer_id());
        statement.setString(index++, DateHandler.JavaDateToSQLDate(document.getExport_date()));
        statement.setString(index++, document.getDescription());
        statement.setLong(index, document.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long GetLastID() throws SQLException, InterruptedException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(Document_ID) FROM islabdb.exportdocument;";
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
        return new ExportDocument();
    }
}