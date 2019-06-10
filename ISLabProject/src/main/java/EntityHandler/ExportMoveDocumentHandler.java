package EntityHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOExportGoods;
import Database.DAO.DAOStorage;
import Entity.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class ExportMoveDocumentHandler implements EntityHandler {

    private static ExportMoveDocumentHandler instance = new ExportMoveDocumentHandler();
    private ExportMoveDocumentHandler() {}
    public static ExportMoveDocumentHandler getInstance() {
        return instance;
    }

    @Override
    public JSONObject getJSON(Entity entity, List<String> representativeData) throws ServletException {
        ExportMoveDocument castedEntity = (ExportMoveDocument) entity;
        JSONObject object = new JSONObject();
        try {
            object.put("id",             castedEntity.getId());
            object.put("exportGoodsId", "(" + castedEntity.getExportGoodsId() + ") " + representativeData.get(0));
            object.put("storageId",     "(" + castedEntity.getStorageId() + ") " + representativeData.get(1));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }

    @Override
    public Entity setByJSON(JSONObject json) throws ServletException {
        ExportMoveDocument entity = new ExportMoveDocument();
        try {
            entity.setId(            (!json.has("id")            || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id"))));
            entity.setExportGoodsId( (!json.has("exportGoodsId") || json.getString("exportGoodsId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("exportGoodsId"))));
            entity.setStorageId(     (!json.has("storageId")     || json.getString("storageId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("storageId"))));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return entity;
    }

    @Override
    public ArrayList<Long> getForeingKeys(Entity entity) {
        ExportMoveDocument castedEntity = (ExportMoveDocument) entity;
        ArrayList<Long> result = new ArrayList<>();
        result.add(castedEntity.getExportGoodsId());
        result.add(castedEntity.getStorageId());
        return result;
    }

    @Override
    public ArrayList<DAOAbstract> getForeingDAO(Entity entity) {
        ArrayList<DAOAbstract> result = new ArrayList<>();
        result.add(DAOExportGoods.getInstance());
        result.add(DAOStorage.getInstance());
        return result;
    }

    @Override
    public String getRepresentantiveData(Entity entity) {
        return null;
    }
}