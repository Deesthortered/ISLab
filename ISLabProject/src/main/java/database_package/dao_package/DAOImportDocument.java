package database_package.dao_package;

import data_model.Entity;
import data_model.ImportDocument;
import utility_package.DateHandler;

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
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        ImportDocument casted_filter = (ImportDocument) filter;
        ArrayList<Entity> result = new ArrayList<>();

        try {
            String sql_query = "SELECT * FROM islabdb.importdocument " +
                    "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                    "(Document_ProviderID = ?  OR ? = "   + Entity.undefined_long + ") AND " +
                    "(Document_ImportDate = ?  OR ? = \'"   + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                    "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setLong(3, casted_filter.getProvider_id());
            statement.setLong(4, casted_filter.getProvider_id());
            statement.setString(5, DateHandler.JavaDateToSQLDate(casted_filter.getImport_date()));
            statement.setString(6, DateHandler.JavaDateToSQLDate(casted_filter.getImport_date()));
            statement.setString(7, casted_filter.getDescription());
            statement.setString(8, casted_filter.getDescription());
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Document_ID");
                long name = resultSet.getLong("Document_ProviderID");
                Date date = DateHandler.SQLDateToJavaDate(resultSet.getString("Document_ImportDate"));
                String description = resultSet.getString("Document_Description");
                result.add(new ImportDocument(id, name, date, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            ImportDocument casted_item = (ImportDocument) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importdocument (Document_ProviderID, Document_ImportDate, Document_Description) VALUES (?, ?, ?);");
                statement.setLong(1, casted_item.getProvider_id());
                statement.setString(2, DateHandler.JavaDateToSQLDate(casted_item.getImport_date()));
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
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importdocument where Document_ID = ?");
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM islabdb.importdocument WHERE Document_ID = ?;");
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
        ImportDocument document = (ImportDocument) entity;
        try {
            String sql_code =   "UPDATE islabdb.importdocument SET " +
                    "Document_ProviderID = ?, " +
                    "Document_ImportDate = ?, " +
                    "Document_Description = ? " +
                    "WHERE Document_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setLong(1, document.getProvider_id());
            statement.setString(2, DateHandler.JavaDateToSQLDate(document.getImport_date()));
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
    public long GetLastID(Connection connection) {
        long res = -1;
        try {
            String sql_code = "SELECT max(Document_ID) FROM islabdb.importdocument;";
            PreparedStatement statement = connection.prepareStatement(sql_code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Entity createEntity() {
        return new ImportDocument();
    }

    public ImportDocument GetEarlierDocument(Connection connection) {
        ImportDocument result = new ImportDocument();
        try {
            String sql_query =
                    "SELECT * " +
                            "FROM islabdb.importdocument " +
                            "WHERE Document_ImportDate = " +
                            "(SELECT MIN(Document_ImportDate) " +
                            "FROM islabdb.importdocument)";
            PreparedStatement statement = connection.prepareStatement(sql_query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("Document_ID");
                long name = resultSet.getLong("Document_ProviderID");
                Date date = DateHandler.SQLDateToJavaDate(resultSet.getString("Document_ImportDate"));
                String description = resultSet.getString("Document_Description");
                result = new ImportDocument(id, name, date, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ImportDocument GetLatestDocument(Connection connection) {
        ImportDocument result = new ImportDocument();
        try {
            String sql_query =
                            "SELECT * " +
                            "FROM islabdb.importdocument " +
                            "WHERE Document_ImportDate = " +
                                    "(SELECT MAX(Document_ImportDate) " +
                                    "FROM islabdb.importdocument)";
            PreparedStatement statement = connection.prepareStatement(sql_query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("Document_ID");
                long name = resultSet.getLong("Document_ProviderID");
                Date date = DateHandler.SQLDateToJavaDate(resultSet.getString("Document_ImportDate"));
                String description = resultSet.getString("Document_Description");
                result = new ImportDocument(id, name, date, description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<ImportDocument> GetDocumentsBetweenDates(Connection connection, Date from, Date to) {
        ArrayList<ImportDocument> result = new ArrayList<>();
        try {
            String sql_query =
                            "SELECT * " +
                            "FROM islabdb.importdocument " +
                            "WHERE ? <= Document_ImportDate AND Document_ImportDate < ?";
            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setString(1, DateHandler.JavaDateToSQLDate(from));
            statement.setString(2, DateHandler.JavaDateToSQLDate(to));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("Document_ID");
                long name = resultSet.getLong("Document_ProviderID");
                Date date = DateHandler.SQLDateToJavaDate(resultSet.getString("Document_ImportDate"));
                String description = resultSet.getString("Document_Description");
                result.add(new ImportDocument(id, name, date, description));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}