package Database;

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
    private LinkedList<Integer> free_connections;
    private Semaphore connectionDistributor;

    private ConnectionPool() throws ClassNotFoundException {
        loadDriver();
        this.connections = new ArrayList<>();
        this.free_connections = new LinkedList<>();
        this.connectionDistributor = new Semaphore(MAX_CONNECTION_COUNT);
    }
    public static synchronized ConnectionPool getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException, SQLException {
        Connection result;
        connectionDistributor.acquire();
        if (free_connections.isEmpty()) {
            int last = connections.size();
            connections.add(DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD));
            result = connections.get(last);
        } else {
            int first_free = free_connections.getFirst();
            free_connections.removeFirst();
            result = connections.get(first_free);
        }
        return result;
    }
    public void dropConnection(Connection connection) {
        if (!connections.contains(connection)) {
            return;
        }
        int i = connections.indexOf(connection);
        free_connections.addFirst(i);
        connectionDistributor.release();
    }

    private void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
}
