package EntityHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOImportGoods;
import Database.DAO.DAOStorage;
import Entity.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class ImportMoveDocumentHandler implements EntityHandler {

    private static ImportMoveDocumentHandler instance = new ImportMoveDocumentHandler();
    private ImportMoveDocumentHandler() {}
    public static ImportMoveDocumentHandler getInstance() {
        return instance;
    }

    @Override
    public JSONObject getJSON(Entity entity, List<String> representativeData) throws ServletException {
        ImportMoveDocument castedEntity = (ImportMoveDocument) entity;
        JSONObject object = new JSONObject();
        try {
            object.put("id",             castedEntity.getId());
            object.put("importGoodsId","(" + castedEntity.getImportGoodsId() + ") " + representativeData.get(0));
            object.put("storageId",    "(" + castedEntity.getStorageId() + ") " + representativeData.get(1));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }

    @Override
    public Entity setByJSON(JSONObject json) throws ServletException {
        ImportMoveDocument entity = new ImportMoveDocument();
        try {
            entity.setId(            (!json.has("id")            || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id"))));
            entity.setImportGoodsId( (!json.has("importGoodsId") || json.getString("importGoodsId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("importGoodsId"))));
            entity.setStorageId(     (!json.has("storageId")     || json.getString("storageId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("storageId"))));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return entity;
    }

    @Override
    public ArrayList<Long> getForeingKeys(Entity entity) {
        ImportMoveDocument castedEntity = (ImportMoveDocument) entity;
        ArrayList<Long> result = new ArrayList<>();
        result.add(castedEntity.getImportGoodsId());
        result.add(castedEntity.getStorageId());
        return result;
    }

    @Override
    public ArrayList<DAOAbstract> getForeingDAO(Entity entity) {
        ArrayList<DAOAbstract> result = new ArrayList<>();
        result.add(DAOImportGoods.getInstance());
        result.add(DAOStorage.getInstance());
        return result;
    }

    @Override
    public String getRepresentantiveData(Entity entity) {
        return null;
    }
}
