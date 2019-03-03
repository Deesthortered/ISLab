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
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.systemusers where user_login = ? and  user_password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.DropConnection(connection);
        int count = 0;
        try {
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            } else {
                throw new NullPointerException("User is not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count == 1;
    }
    public int getUserRole(String login) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ResultSet resultSet = null;

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT user_role from islabdb.systemusers where user_login = ?");
            statement.setString(1, login);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pool.DropConnection(connection);
        int user_role = -1;
        try {
            if (resultSet.next()) {
                user_role = resultSet.getInt("user_role");
            } else {
                throw new NullPointerException("User is not exist");
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return user_role;
    }
}
