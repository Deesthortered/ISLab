package data_model;

import org.json.JSONException;
import org.json.JSONObject;

public class Provider implements Entity {
    private long   id;
    private String name;
    private String country;
    private String description;

    public Provider() {
        this.id          = Entity.undefined_long;
        this.name        = Entity.undefined_string;
        this.country     = Entity.undefined_string;
        this.description = Entity.undefined_string;
    }
    public Provider(long id, String name, String country, String description) {
        this.id = id;

        if (name == null || name.equals(""))
            this.name = Entity.undefined_string;
        else this.name = name;

        if (country == null || country.equals(""))
            this.country = Entity.undefined_string;
        else this.country = country;

        if (description == null)
            this.description = Entity.undefined_string;
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
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id", id);
            object.put("name", name);
            object.put("country", country);
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
            this.country     = (json.getString("country").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("country"));
            this.description = (json.getString("description").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
