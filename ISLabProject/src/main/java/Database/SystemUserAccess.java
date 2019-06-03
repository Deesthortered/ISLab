package Database;

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

    public boolean confirmationAuthoritarian(Connection connection, String login, String password) throws SQLException {
        String sqlQuery = "SELECT COUNT(*) from islabdb.systemusers where user_login = ? and  user_password = ?";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setString(index++, login);
        statement.setString(index, password);
        ResultSet resultSet = statement.executeQuery();

        int count = 0;
        assert resultSet != null;
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        } else {
            throw new SQLException("User is not exist");
        }
        return count == 1;
    }
    public int getUserRole(Connection connection, String login) throws SQLException {
        String sql_query = "SELECT user_role from islabdb.systemusers where user_login = ?";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();

        int user_role;
        assert resultSet != null;
        if (resultSet.next()) {
            user_role = resultSet.getInt("user_role");
        } else {
            throw new SQLException("User is not exist");
        }

        return user_role;
    }
}
