package database_package.dao_package;

import data_model.Entity;
import data_model.ExportDocument;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static utility_package.Common.JavaDateToSQLDate;

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
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        ExportDocument casted_filter = (ExportDocument) filter;
        ArrayList<Entity> result = new ArrayList<>();
        try {
            String sql_query = "SELECT * FROM islabdb.exportdocument " +
                    "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                    "(Document_CustomerID = ?  OR ? = " + Entity.undefined_long   + ") AND " +
                    "(Document_ExportDate = ?  OR ? = \'" + JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                    "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setLong(3, casted_filter.getCustomer_id());
            statement.setLong(4, casted_filter.getCustomer_id());
            statement.setString(5, JavaDateToSQLDate(casted_filter.getExport_date()));
            statement.setString(6, JavaDateToSQLDate(casted_filter.getExport_date()));
            statement.setString(7, casted_filter.getDescription());
            statement.setString(8, casted_filter.getDescription());
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Document_ID");
                long name = resultSet.getLong("Document_CustomerID");
                Date date = resultSet.getDate("Document_ExportDate");
                String description = resultSet.getString("Document_Description");
                result.add(new ExportDocument(id, name, date, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            ExportDocument casted_item = (ExportDocument) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportdocument (Document_CustomerID, Document_ExportDate, Document_Description) VALUES (?, ?, ?);");
                statement.setLong(1, casted_item.getCustomer_id());
                statement.setString(2, JavaDateToSQLDate(casted_item.getExport_date()));
                statement.setString(3, casted_item.getDescription());
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
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.exportdocument where Document_ID = ?");
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM islabdb.exportdocument WHERE Document_ID = ?;");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean DeleteEntityList(Connection connection, Entity filter) {
        return false;
    }
    @Override
    public boolean EditEntity(Connection connection, Entity entity) {
        ExportDocument document = (ExportDocument) entity;
        try {
            String sql_code =   "UPDATE islabdb.exportdocument SET " +
                    "Document_CustomerID = ?, " +
                    "Document_ExportDate = ?, " +
                    "Document_Description = ? " +
                    "WHERE Document_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setLong(1, document.getCustomer_id());
            statement.setString(2, JavaDateToSQLDate(document.getExport_date()));
            statement.setString(3, document.getDescription());
            statement.setLong(4, document.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Entity createEntity() {
        return new ExportDocument();
    }
}
