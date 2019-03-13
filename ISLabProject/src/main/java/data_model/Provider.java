package data_model;

import org.json.JSONException;
import org.json.JSONObject;

public class Provider {
    private long   id;
    private String name;
    private String country;
    private String description;

    public Provider() {
        this.id = -1;
        this.name = null;
        this.country = null;
        this.description = null;
    }
    public Provider(long id, String name, String country, String description) {
        this.id = id;
        this.name = name;
        this.country = country;
        if (description == null || description.equals(""))
            this.description = null;
        else this.description = description;
    }
    public Provider(String name, String country, String description) {
        this.id = -1;
        this.name = name;
        this.country = country;
        if (description == null || description.equals(""))
            this.description = null;
        else this.description = description;
    }

    public long   getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCountry() {
        return country;
    }
    public String getDescription() {
        if (description == null)
            return "";
        return description;
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id", id);
            object.put("name", name);
            object.put("country", country);
            object.put("description",description == null ? "" : description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
