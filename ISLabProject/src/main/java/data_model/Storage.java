package data_model;

import org.json.JSONException;
import org.json.JSONObject;

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
    public JSONObject getJSON() {
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
            this.id          = (json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.name        = (json.getString("name").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("name"));
            this.description = (json.getString("description").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}