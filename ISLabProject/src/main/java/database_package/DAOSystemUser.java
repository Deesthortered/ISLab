package database_package;

import java.sql.*;

public class DAOSystemUser {

    private static DAOSystemUser instance;

    private DAOSystemUser() {

    }
    public static synchronized DAOSystemUser getInstance() {
        if (instance == null) {
            instance = new DAOSystemUser();
        }
        return instance;
    }

    public boolean ConfirmateAuthorizaion(String login, String password) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT user_role from islabdb.systemusers where user_login = ? and  user_password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.DropConnection(connection);
        try {
            assert resultSet != null;
            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
