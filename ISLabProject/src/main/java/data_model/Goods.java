package data_model;

import org.json.JSONException;
import org.json.JSONObject;

public class Goods {
    private long   id;
    private String name;
    private long   average_price;
    private String description;

    public Goods() {
        this.id = -1;
        this.name = null;
        this.average_price = -1;
        this.description = null;
    }
    public Goods(long id, String name, long average_price, String description) {
        this.id = id;

        if (name == null || name.equals(""))
            this.name = null;
        else this.name = name;

        this.average_price = average_price;

        if (description == null || description.equals(""))
            this.description = null;
        else this.description = description;
    }

    public long   getId() {
        return id;
    }
    public String getName() {
        if (name == null)
            return "";
        return name;
    }
    public long   getAverage_price() {
        return average_price;
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
            object.put("average_price", average_price);
            object.put("description", description == null ? "" : description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
