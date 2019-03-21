package data_model;

import org.json.JSONException;
import org.json.JSONObject;

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

    public long getId() {
        return id;
    }
    public long getImportGoods_id() {
        return importGoods_id;
    }
    public long getStorage_id() {
        return storage_id;
    }

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
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",             id);
            object.put("importGoods_id", importGoods_id);
            object.put("storage_id",     storage_id);
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
}
