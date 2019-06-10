package EntityHandler;

import Database.DAO.DAOAbstract;
import Entity.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class GoodsHandler implements EntityHandler {

    private static GoodsHandler instance = new GoodsHandler();
    private GoodsHandler() {}
    public static GoodsHandler getInstance() {
        return instance;
    }

    @Override
    public JSONObject getJSON(Entity entity, List<String> representativeData) throws ServletException {
        Goods castedEntity = (Goods) entity;
        JSONObject object = new JSONObject();
        try {
            object.put("id",           castedEntity.getId());
            object.put("name",         castedEntity.getName());
            object.put("averagePrice", castedEntity.getAveragePrice());
            object.put("description",  castedEntity.getDescription());
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }

    @Override
    public Entity setByJSON(JSONObject json) throws ServletException {
        Goods entity = new Goods();
        try {
            entity.setId(           (!json.has("id")           || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id"))));
            entity.setName(         (!json.has("name")         || json.getString("name").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("name")));
            entity.setAveragePrice( (!json.has("averagePrice") || json.getString("averagePrice").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("averagePrice"))));
            entity.setDescription(  (!json.has("description")  || json.getString("description").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("description")));
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
        Goods castedEntity = (Goods) entity;
        return castedEntity.getName();
    }
}