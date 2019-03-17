package database_package;

import data_model.Entity;
import data_model.ExportDocument;
import utility_package.Common;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DAOExportDocument {

    private static DAOExportDocument instance;

    private DAOExportDocument() {

    }
    public static synchronized DAOExportDocument getInstance() {
        if (instance == null) {
            instance = new DAOExportDocument();
        }
        return instance;
    }

    public ArrayList<ExportDocument> GetImportDocumentList(Connection connection, ExportDocument filter, boolean limited, int start_index, int count_of_records) {
        ArrayList<ExportDocument> result = new ArrayList<>();

        try {
            String sql_query = "SELECT * FROM islabdb.exportdocument " +
                      "WHERE (Document_ID = ?          OR ? = "   + Entity.undefined_long   +   ") AND " +
                            "(Document_CustomerID = ?  OR ? = \'" + Entity.undefined_long   + "\') AND " +
                            "(Document_ExportDate = ?  OR ? = \'" + Entity.undefined_date   + "\') AND " +
                            "(Document_Description = ? OR ? = \'" + Entity.undefined_string + "\')" +
                      ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, filter.getId());
            statement.setLong(2, filter.getId());
            statement.setLong(3, filter.getCustomer_id());
            statement.setLong(4, filter.getCustomer_id());
            statement.setString(5, Common.JavaDateToSQLDate(filter.getExport_date()));
            statement.setString(6, Common.JavaDateToSQLDate(filter.getExport_date()));
            statement.setString(7, filter.getDescription());
            statement.setString(8, filter.getDescription());
            if (limited) {
                statement.setLong(9, count_of_records);
                statement.setLong(10, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Document_ID");
                long name = resultSet.getLong("Document_CustomerID");
                Date date = Common.SQLDateToJavaDate(resultSet.getString("Document_ExportDate"));
                String description = resultSet.getString("Document_Description");
                result.add(new ExportDocument(id, name, date, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
