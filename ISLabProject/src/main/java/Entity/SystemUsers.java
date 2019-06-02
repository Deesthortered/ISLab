package Entity;

import java.util.Date;

public class SystemUsers {
    private String login;
    private String password;
    private int    role;
    private Date   last_visit;

    public SystemUsers(String login, String password, int role, Date last_visit) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.last_visit = last_visit;
    }
}
