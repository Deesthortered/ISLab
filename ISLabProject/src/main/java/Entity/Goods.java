package Entity;

import Database.DAO.DAOAbstract;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class Goods implements Entity {
    private long   id;
    private String name;
    private long   averagePrice;
    private String description;

    public Goods() {
        this.id           = Entity.UNDEFINED_LONG;
        this.name         = Entity.UNDEFINED_STRING;
        this.averagePrice = Entity.UNDEFINED_LONG;
        this.description  = Entity.UNDEFINED_STRING;
    }
    public Goods(long id, String name, long averagePrice, String description) {
        this.id = id;
        if (name == null || name.equals(""))
            this.name = Entity.UNDEFINED_STRING;
        else this.name = name;
        this.averagePrice = averagePrice;
        if (description == null)
            this.description = Entity.UNDEFINED_STRING;
        else this.description = description;
    }

    @Override
    public long   getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public long   getAveragePrice() {
        return averagePrice;
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
    public void setAveragePrice(long averagePrice) {
        this.averagePrice = averagePrice;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject getJSON(List<String> representativeData) throws ServletException {
        JSONObject object = new JSONObject();
        try {
            object.put("id",           id);
            object.put("name",         name);
            object.put("averagePrice", averagePrice);
            object.put("description",  description);
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) throws ServletException {
        try {
            this.id           = (!json.has("id")           || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id")));
            this.name         = (!json.has("name")         || json.getString("name").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("name"));
            this.averagePrice = (!json.has("averagePrice") || json.getString("averagePrice").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("averagePrice")));
            this.description  = (!json.has("description")  || json.getString("description").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("description"));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    public String getRepresentantiveData() {
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
