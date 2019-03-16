package database_package;

import java.sql.*;
import java.util.ArrayList;

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


}
