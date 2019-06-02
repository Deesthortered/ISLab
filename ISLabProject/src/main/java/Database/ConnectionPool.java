package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ConnectionPool {
    private static final int MAX_CONNECTION_COUNT = 20;
    private static final String username      = "admin";
    private static final String password      = "admin";
    private static final String connectionURL = "jdbc:mysql://localhost:3306/mysql";

    private static ConnectionPool instance;
    private ArrayList<Connection> connections;
    private LinkedList<Integer> free_connections;
    private Semaphore connection_distributor;

    private ConnectionPool() throws ClassNotFoundException {
        LoadDriver();
        this.connections = new ArrayList<>();
        this.free_connections = new LinkedList<>();
        this.connection_distributor = new Semaphore(MAX_CONNECTION_COUNT);
    }
    public static synchronized ConnectionPool getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection GetConnection() throws InterruptedException, SQLException {
        Connection result = null;
        connection_distributor.acquire();
        if (free_connections.isEmpty()) {
            int last = connections.size();
            connections.add(DriverManager.getConnection(connectionURL, username, password));
            result = connections.get(last);
        } else {
            int first_free = free_connections.getFirst();
            free_connections.removeFirst();
            result = connections.get(first_free);
        }
        return result;
    }
    public void DropConnection(Connection connection) {
        if (!connections.contains(connection))
            return;
        int i = connections.indexOf(connection);
        free_connections.addFirst(i);
        connection_distributor.release();
    }

    private void LoadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
}
