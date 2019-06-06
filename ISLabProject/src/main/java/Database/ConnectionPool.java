package Database;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ConnectionPool {
    private static final int MAX_CONNECTION_COUNT = 20;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/mysql";

    private static ConnectionPool instance;
    private ArrayList<Connection> connections;
    private LinkedList<Integer> freeConnections;

    private ConnectionPool() throws ClassNotFoundException {
        loadDriver();
        this.connections = new ArrayList<>();
        this.freeConnections = new LinkedList<>();
    }
    public static synchronized ConnectionPool getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException, ServletException {
        Connection result;
        if (freeConnections.isEmpty()) {
            int last = connections.size();
            if (last >= MAX_CONNECTION_COUNT) {
                throw new ServletException("No free connections");
            }
            connections.add(DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD));
            result = connections.get(last);
        } else {
            int first_free = freeConnections.getFirst();
            freeConnections.removeFirst();
            result = connections.get(first_free);
        }
        return result;
    }
    public void dropConnection(Connection connection) {
        if (!connections.contains(connection)) {
            return;
        }
        int i = connections.indexOf(connection);
        freeConnections.addFirst(i);
    }

    private void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
}
