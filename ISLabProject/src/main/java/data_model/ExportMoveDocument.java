package data_model;

import org.json.JSONException;
import org.json.JSONObject;

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
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",             id);
            object.put("exportGoods_id", exportGoods_id);
            object.put("storage_id",     storage_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
