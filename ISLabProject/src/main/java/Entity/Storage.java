package Entity;

import Database.DAO.DAOAbstract;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Storage implements Entity {
    private long   id;
    private String name;
    private String description;

    public Storage() {
        this.id          = Entity.undefined_long;
        this.name        = Entity.undefined_string;
        this.description = Entity.undefined_string;
    }
    public Storage(long id, String name, String description) {
        this.id = id;

        if (name == null || name.equals(""))
            this.name = Entity.undefined_string;
        else this.name = name;

        if (description == null)
            this.description = Entity.undefined_string;
        else this.description = description;
    }

    @Override
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject getJSON(ArrayList<String> represantive_data) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("name",        name);
            object.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) {
        try {
            this.id          = (!json.has("id") || json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.name        = (!json.has("name") || json.getString("name").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("name"));
            this.description = (!json.has("description") || json.getString("description").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRepresantiveData() {
        return name;
    }
    @Override
    public ArrayList<DAOAbstract> getForeingDAO() {
        return null;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        return null;
    }
}