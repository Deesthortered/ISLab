package EntityHandler;

import Database.DAO.DAOAbstract;
import Entity.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class CustomerHandler implements EntityHandler {

    private static CustomerHandler instance = new CustomerHandler();
    private CustomerHandler() {}
    public static CustomerHandler getInstance() {
        return instance;
    }

    @Override
    public JSONObject getJSON(Entity entity, List<String> representativeData) throws ServletException {
        Customer castedEntity = (Customer) entity;
        JSONObject object = new JSONObject();
        try {
            object.put("id",          castedEntity.getId());
            object.put("name",        castedEntity.getName());
            object.put("country",     castedEntity.getCountry());
            object.put("description", castedEntity.getDescription());
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }
    @Override
    public Entity setByJSON(JSONObject json) throws ServletException {
        Customer entity = new Customer();
        try {
            entity.setId(          (!json.has("id")          || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id"))));
            entity.setName(        (!json.has("name")        || json.getString("name").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("name")));
            entity.setCountry(     (!json.has("country")     || json.getString("country").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("country")));
            entity.setDescription((!json.has("description") || json.getString("description").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("description")));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return entity;
    }

    @Override
    public ArrayList<Long> getForeingKeys(Entity entity) {
        return null;
    }

    @Override
    public ArrayList<DAOAbstract> getForeingDAO(Entity entity) {
        return null;
    }

    @Override
    public String getRepresentantiveData(Entity entity) {
        Customer castedEntity = (Customer) entity;
        return castedEntity.getName();
    }
}
