package Database;

import javax.servlet.ServletException;
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

    public boolean confirmationAuthoritarian(String login, String password) throws SQLException, ServletException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
        } catch (ClassNotFoundException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }

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
        pool.dropConnection(connection);
        return count == 1;
    }
    public int getUserRole(String login) throws SQLException, ServletException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
        } catch (ClassNotFoundException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }

        String sqlQuery = "SELECT user_role from islabdb.systemusers where user_login = ?";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();

        int userRole;
        assert resultSet != null;
        if (resultSet.next()) {
            userRole = resultSet.getInt("user_role");
        } else {
            throw new SQLException("User is not exist");
        }
        pool.dropConnection(connection);
        return userRole;
    }
}
