package Entity;

import Database.DAO.DAOAbstract;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class Provider implements Entity {
    private long   id;
    private String name;
    private String country;
    private String description;

    public Provider() {
        this.id          = Entity.UNDEFINED_LONG;
        this.name        = Entity.UNDEFINED_STRING;
        this.country     = Entity.UNDEFINED_STRING;
        this.description = Entity.UNDEFINED_STRING;
    }
    public Provider(long id, String name, String country, String description) {
        this.id = id;
        if (name == null || name.equals(""))
            this.name = Entity.UNDEFINED_STRING;
        else this.name = name;
        if (country == null || country.equals(""))
            this.country = Entity.UNDEFINED_STRING;
        else this.country = country;
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
    public String getCountry() {
        return country;
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
    public void setCountry(String country) {
        this.country = country;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject getJSON(List<String> representativeData) throws ServletException {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("name",        name);
            object.put("country",     country);
            object.put("description", description);
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) throws ServletException {
        try {
            this.id          = (!json.has("id")          || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id")));
            this.name        = (!json.has("name")        || json.getString("name").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("name"));
            this.country     = (!json.has("country")     || json.getString("country").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("country"));
            this.description = (!json.has("description") || json.getString("description").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("description"));
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
