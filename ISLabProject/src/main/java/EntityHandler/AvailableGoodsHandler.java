package EntityHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOGoods;
import Database.DAO.DAOProvider;
import Database.DAO.DAOStorage;
import Entity.*;
import Utility.DateHandler;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class AvailableGoodsHandler implements EntityHandler {

    private static AvailableGoodsHandler instance = new AvailableGoodsHandler();
    private AvailableGoodsHandler() {}
    public static AvailableGoodsHandler getInstance() {
        return instance;
    }

    @Override
    public JSONObject getJSON(Entity entity, List<String> representativeData) throws ServletException {
        AvailableGoods castedEntity = (AvailableGoods) entity;
        JSONObject object = new JSONObject();
        try {
            object.put("id",          castedEntity.getId());
            object.put("goodsId",    "(" + castedEntity.getGoodsId() + ") " + representativeData.get(0));
            object.put("goodsCount",  castedEntity.getGoodsCount());
            object.put("providerId", "(" + castedEntity.getProviderId() + ") " + representativeData.get(1));
            object.put("storageId",  "(" + castedEntity.getStorageId() + ") " + representativeData.get(2));
            object.put("current",      castedEntity.isCurrent());
            object.put("snapshotDate", DateHandler.javaDateToSQLDate(castedEntity.getSnapshotDate()));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }
    @Override
    public Entity setByJSON(JSONObject json) throws ServletException {
        if (json == null)
            throw new ServletException("Empty JSON");
        AvailableGoods entity = new AvailableGoods();
        try {
            entity.setId(           (!json.has("id")           || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id"))));
            entity.setGoodsId(      (!json.has("goodsId")      || json.getString("goodsId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsId"))));
            entity.setGoodsCount(   (!json.has("goodsCount")   || json.getString("goodsCount").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsCount"))));
            entity.setProviderId(   (!json.has("providerId")   || json.getString("providerId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("providerId"))));
            entity.setStorageId(    (!json.has("storageId")    || json.getString("storageId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("storageId"))));
            entity.setCurrent(      (!json.has("current")      ||!json.getString("current").equals(Entity.UNDEFINED_STRING) && json.getBoolean("current")));
            entity.setSnapshotDate( (!json.has("snapshotDate") || json.getString("snapshotDate").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_DATE : DateHandler.sqlDateToJavaDate(json.getString("snapshotDate"))));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return entity;
    }

    @Override
    public String getRepresentantiveData(Entity entity) {
        return null;
    }
    @Override
    public ArrayList<DAOAbstract> getForeingDAO(Entity entity) {
        ArrayList<DAOAbstract> result = new ArrayList<>();
        result.add(DAOGoods.getInstance());
        result.add(DAOProvider.getInstance());
        result.add(DAOStorage.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys(Entity entity) {
        ArrayList<Long> result = new ArrayList<>();
        AvailableGoods castedEntity = (AvailableGoods) entity;
        result.add(castedEntity.getGoodsId());
        result.add(castedEntity.getProviderId());
        result.add(castedEntity.getStorageId());
        return result;
    }
}