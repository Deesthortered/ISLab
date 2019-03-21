package database_package;

import data_model.Entity;
import data_model.ImportDocument;
import utility_package.Common;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static utility_package.Common.JavaDateToSQLDate;

public class DAOImportDocument {

    private static DAOImportDocument instance;

    private DAOImportDocument() {

    }
    public static synchronized DAOImportDocument getInstance() {
        if (instance == null) {
            instance = new DAOImportDocument();
        }
        return instance;
    }

    public ArrayList<ImportDocument> GetImportDocumentList(Connection connection, ImportDocument filter, boolean limited, int start_index, int count_of_records) {
        ArrayList<ImportDocument> result = new ArrayList<>();

        try {
            String sql_query = "SELECT * FROM islabdb.importdocument " +
                      "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long + ") AND " +
                            "(Document_ProviderID = ?  OR ? = "   + Entity.undefined_long + ") AND " +
                            "(Document_ImportDate = ?  OR ? = \'"   + JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                            "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                      ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, filter.getId());
            statement.setLong(2, filter.getId());
            statement.setLong(3, filter.getProvider_id());
            statement.setLong(4, filter.getProvider_id());
            statement.setString(5, JavaDateToSQLDate(filter.getImport_date()));
            statement.setString(6, JavaDateToSQLDate(filter.getImport_date()));
            statement.setString(7, filter.getDescription());
            statement.setString(8, filter.getDescription());
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Document_ID");
                long name = resultSet.getLong("Document_ProviderID");
                Date date = resultSet.getDate("Document_ImportDate");
                String description = resultSet.getString("Document_Description");
                result.add(new ImportDocument(id, name, date, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean IsExistsDocument(Connection connection, long id) {
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
    public boolean AddDocument(Connection connection, ImportDocument importDocument) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importdocument (Document_ProviderID, Document_ImportDate, Document_Description) VALUES (?, ?, ?);");
            statement.setLong(1, importDocument.getProvider_id());
            statement.setString(2, JavaDateToSQLDate(importDocument.getImport_date()));
            statement.setString(3, importDocument.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
