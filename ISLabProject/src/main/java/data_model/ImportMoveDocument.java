package data_model;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOImportGoods;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImportMoveDocument implements Entity {
    private long id;
    private long importGoods_id;
    private long storage_id;

    public ImportMoveDocument() {
        this.id             = Entity.undefined_long;
        this.importGoods_id = Entity.undefined_long;
        this.storage_id     = Entity.undefined_long;
    }
    public ImportMoveDocument(long id, long importGoods_id, long storage_id) {
        this.id             = id;
        this.importGoods_id = importGoods_id;
        this.storage_id     = storage_id;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getImportGoods_id() {
        return importGoods_id;
    }
    public long getStorage_id() {
        return storage_id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setImportGoods_id(long importGoods_id) {
        this.importGoods_id = importGoods_id;
    }
    public void setStorage_id(long storage_id) {
        this.storage_id = storage_id;
    }

    @Override
    public JSONObject getJSON(ArrayList<String> represantive_data) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",             id);
            object.put("importGoods_id", "(" + importGoods_id + ") " + represantive_data.get(0));
            object.put("storage_id",     "(" + storage_id + ") " + represantive_data.get(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) {
        try {
            this.id             = (json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.importGoods_id = (json.getString("importGoods_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("importGoods_id")));
            this.storage_id     = (json.getString("storage_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("storage_id")));
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
        result.add(DAOImportGoods.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        ArrayList<Long> result = new ArrayList<>();
        result.add(importGoods_id);
        result.add(storage_id);
        return result;
    }
}
