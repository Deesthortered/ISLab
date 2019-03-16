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
        this.name = name;
        if (description == null || description.equals(""))
            this.description = null;
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
            object.put("id", id);
            object.put("name", name);
            object.put("description", description == null ? "" : description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}