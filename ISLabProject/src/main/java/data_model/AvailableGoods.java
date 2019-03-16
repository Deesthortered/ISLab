package data_model;

import org.json.JSONException;
import org.json.JSONObject;

public class AvailableGoods implements Entity {
    private long id;
    private long goods_id;
    private long provider_id;
    private long storage_id;

    public AvailableGoods() {
        this.id          = Entity.undefined_long;
        this.goods_id    = Entity.undefined_long;
        this.provider_id = Entity.undefined_long;
        this.storage_id  = Entity.undefined_long;
    }
    public AvailableGoods(long id, long goods_id, long provider_id, long storage_id) {
        this.id          = id;
        this.goods_id    = goods_id;
        this.provider_id = provider_id;
        this.storage_id  = storage_id;
    }

    public long getId() {
        return id;
    }
    public long getGoods_id() {
        return goods_id;
    }
    public long getProvider_id() {
        return provider_id;
    }
    public long getStorage_id() {
        return storage_id;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("goods_id",    goods_id);
            object.put("provider_id", provider_id);
            object.put("storage_id",  storage_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}