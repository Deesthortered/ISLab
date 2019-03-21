package data_model;

import org.json.JSONObject;
import java.util.Date;

public class SystemUsers implements Entity {
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

    @Override
    public JSONObject getJSON() {
        return null;
    }
    @Override
    public void setByJSON(JSONObject json) {

    }
}
