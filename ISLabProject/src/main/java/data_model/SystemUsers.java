package data_model;

public class SystemUsers {
    private String login;
    private String password;
    private int    role;

    public SystemUsers(String login, String password, int role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
