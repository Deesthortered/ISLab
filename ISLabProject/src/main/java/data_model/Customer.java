package data_model;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer implements Entity {
    private long   id;
    private String name;
    private String country;
    private String description;

    public Customer() {
        this.id          = -1;
        this.name        = Entity.undefined_string;
        this.country     = Entity.undefined_string;
        this.description = Entity.undefined_string;
    }
    public Customer(long id, String name, String country, String description) {
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
        if (description.equals(Entity.undefined_string))
            return "";
        return description;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("name",        name);
            object.put("country",     country);
            object.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
