package database_package;

import java.sql.*;

public class SystemUserAccess {

    private static SystemUserAccess instance;

    private SystemUserAccess() {

    }
    public static synchronized SystemUserAccess getInstance() {
        if (instance == null) {
            instance = new SystemUserAccess();
        }
        return instance;
    }

    public boolean ConfirmationAuthoritarian(Connection connection, String login, String password) {
        ResultSet resultSet = null;
        try {
            String sql_query = "SELECT COUNT(*) from islabdb.systemusers where user_login = ? and  user_password = ?";

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int count = 0;
        try {
            assert resultSet != null;
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
            String sql_query = "SELECT user_role from islabdb.systemusers where user_login = ?";

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int user_role = -1;
        try {
            assert resultSet != null;
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
