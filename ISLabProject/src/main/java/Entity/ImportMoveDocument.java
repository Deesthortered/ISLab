package Entity;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOImportGoods;
import Database.DAO.DAOStorage;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class ImportMoveDocument implements Entity {
    private long id;
    private long importGoodsId;
    private long storageId;

    public ImportMoveDocument() {
        this.id            = Entity.UNDEFINED_LONG;
        this.importGoodsId = Entity.UNDEFINED_LONG;
        this.storageId     = Entity.UNDEFINED_LONG;
    }
    public ImportMoveDocument(long id, long importGoodsId, long storageId) {
        this.id            = id;
        this.importGoodsId = importGoodsId;
        this.storageId     = storageId;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getImportGoodsId() {
        return importGoodsId;
    }
    public long getStorageId() {
        return storageId;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setImportGoodsId(long importGoodsId) {
        this.importGoodsId = importGoodsId;
    }
    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }

    @Override
    public JSONObject getJSON(List<String> representativeData) throws ServletException {
        JSONObject object = new JSONObject();
        try {
            object.put("id",             id);
            object.put("importGoodsId","(" + importGoodsId + ") " + representativeData.get(0));
            object.put("storageId",    "(" + storageId + ") " + representativeData.get(1));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) throws ServletException {
        try {
            this.id            = (!json.has("id")            || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id")));
            this.importGoodsId = (!json.has("importGoodsId") || json.getString("importGoodsId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("importGoodsId")));
            this.storageId     = (!json.has("storageId")     || json.getString("storageId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("storageId")));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    public String getRepresentantiveData() {
        return null;
    }
    @Override
    public ArrayList<DAOAbstract> getForeingDAO() {
        ArrayList<DAOAbstract> result = new ArrayList<>();
        result.add(DAOImportGoods.getInstance());
        result.add(DAOStorage.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        ArrayList<Long> result = new ArrayList<>();
        result.add(importGoodsId);
        result.add(storageId);
        return result;
    }
}
