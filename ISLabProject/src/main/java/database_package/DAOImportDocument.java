package database_package;

import data_model.ImportDocument;

import java.sql.*;
import java.util.ArrayList;

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
        return result;
    }

}
