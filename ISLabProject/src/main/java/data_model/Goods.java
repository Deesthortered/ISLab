package data_model;

import org.json.JSONException;
import org.json.JSONObject;

public class Goods implements Entity {
    private long   id;
    private String name;
    private long   average_price;
    private String description;

    public Goods() {
        this.id            = Entity.undefined_long;
        this.name          = Entity.undefined_string;
        this.average_price = Entity.undefined_long;
        this.description   = Entity.undefined_string;
    }
    public Goods(long id, String name, long average_price, String description) {
        this.id = id;

        if (name == null || name.equals(""))
            this.name = Entity.undefined_string;
        else this.name = name;

        this.average_price = average_price;

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
    public long   getAverage_price() {
        return average_price;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",            id);
            object.put("name",          name);
            object.put("average_price", average_price);
            object.put("description",   description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) {
        try {
            this.id            = (json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.name          = (json.getString("name").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("name"));
            this.average_price = (json.getString("average_price").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("average_price")));
            this.description   = (json.getString("description").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
