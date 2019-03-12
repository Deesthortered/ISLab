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

    public boolean ConfirmationAuthoritarian(Connection connection, String login, String password) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.systemusers where user_login = ? and  user_password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public int getUserRole(Connection connection, String login) {
        ResultSet resultSet = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT user_role from islabdb.systemusers where user_login = ?");
            statement.setString(1, login);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
