package data_model;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOExportGoods;
import database_package.dao_package.DAOStorage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExportMoveDocument implements Entity {
    private long id;
    private long exportGoods_id;
    private long storage_id;

    public ExportMoveDocument() {
        this.id             = Entity.undefined_long;
        this.exportGoods_id = Entity.undefined_long;
        this.storage_id     = Entity.undefined_long;
    }
    public ExportMoveDocument(long id, long exportGoods_id, long storage_id) {
        this.id             = id;
        this.exportGoods_id = exportGoods_id;
        this.storage_id     = storage_id;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getExportGoods_id() {
        return exportGoods_id;
    }
    public long getStorage_id() {
        return storage_id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setExportGoods_id(long exportGoods_id) {
        this.exportGoods_id = exportGoods_id;
    }
    public void setStorage_id(long storage_id) {
        this.storage_id = storage_id;
    }

    @Override
    public JSONObject getJSON(ArrayList<String> represantive_data) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",             id);
            object.put("exportGoods_id", "(" + exportGoods_id + ") " + represantive_data.get(0));
            object.put("storage_id",     "(" + storage_id + ") " + represantive_data.get(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) {
        try {
            this.id             = (!json.has("id") || json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.exportGoods_id = (!json.has("exportGoods_id") || json.getString("exportGoods_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("exportGoods_id")));
            this.storage_id     = (!json.has("storage_id") || json.getString("storage_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("storage_id")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRepresantiveData() {
        return null;
    }
    @Override
    public ArrayList<DAOAbstract> getForeingDAO() {
        ArrayList<DAOAbstract> result = new ArrayList<>();
        result.add(DAOExportGoods.getInstance());
        result.add(DAOStorage.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        ArrayList<Long> result = new ArrayList<>();
        result.add(exportGoods_id);
        result.add(storage_id);
        return result;
    }
}
