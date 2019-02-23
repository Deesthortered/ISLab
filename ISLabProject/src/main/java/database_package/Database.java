package database_package;

// Singleton pattern

public class Database {
    private static Database instanse;

    static final String username = "admin";
    static final String password = "admin";
    static final String connectionURL = "jdbc:mysql://localhost:3306/mysql";

    private Database() {
        Initialization();
    }
    public Database getInstanse() {
        synchronized (instanse) {
            if (instanse == null) {
                instanse = new Database();
            }
        }
        return instanse;
    }

    private void Initialization() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}
