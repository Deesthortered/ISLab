package EntityHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOExportDocument;
import Database.DAO.DAOGoods;
import Entity.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class ExportGoodsHandler implements EntityHandler {

    private static ExportGoodsHandler instance = new ExportGoodsHandler();
    private ExportGoodsHandler() {}
    public static ExportGoodsHandler getInstance() {
        return instance;
    }

    @Override
    public JSONObject getJSON(Entity entity, List<String> representativeData) throws ServletException {
        ExportGoods castedEntity = (ExportGoods) entity;
        JSONObject object = new JSONObject();
        try {
            object.put("id",          castedEntity.getId());
            object.put("documentId", "(" + castedEntity.getDocumentId() + ") " + representativeData.get(0));
            object.put("goodsId",    "(" + castedEntity.getGoodsId() + ") " + representativeData.get(1));
            object.put("goodsCount", castedEntity.getGoodsCount());
            object.put("goodsPrice", castedEntity.getGoodsPrice());
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }

    @Override
    public Entity setByJSON(JSONObject json) throws ServletException {
        ExportGoods entity = new ExportGoods();
        try {
            entity.setId(         (!json.has("id")         || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id"))));
            entity.setDocumentId( (!json.has("documentId") || json.getString("documentId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("documentId"))));
            entity.setGoodsId(    (!json.has("goodsId")    || json.getString("goodsId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsId"))));
            entity.setGoodsCount( (!json.has("goodsCount") || json.getString("goodsCount").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsCount"))));
            entity.setGoodsPrice( (!json.has("goodsPrice") || json.getString("goodsPrice").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsPrice"))));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return entity;
    }

    @Override
    public ArrayList<Long> getForeingKeys(Entity entity) {
        ExportGoods castedEntity = (ExportGoods) entity;
        ArrayList<Long> result = new ArrayList<>();
        result.add(castedEntity.getDocumentId());
        result.add(castedEntity.getGoodsId());
        return result;
    }

    @Override
    public ArrayList<DAOAbstract> getForeingDAO(Entity entity) {
        ArrayList<DAOAbstract> result = new ArrayList<>();
        result.add(DAOExportDocument.getInstance());
        result.add(DAOGoods.getInstance());
        return result;
    }

    @Override
    public String getRepresentantiveData(Entity entity) {
        return null;
    }
}