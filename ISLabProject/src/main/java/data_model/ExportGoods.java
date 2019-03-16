package data_model;

import org.json.JSONException;
import org.json.JSONObject;

public class ExportGoods implements Entity {
    private long id;
    private long document_id;
    private long goods_id;
    private long goods_count;
    private long goods_price;

    public ExportGoods() {
        this.id          = Entity.undefined_long;
        this.document_id = Entity.undefined_long;
        this.goods_id    = Entity.undefined_long;
        this.goods_count = Entity.undefined_long;
        this.goods_price = Entity.undefined_long;
    }
    public ExportGoods(long id, long document_id, long goods_id, long goods_count, long goods_price) {
        this.id          = id;
        this.document_id = document_id;
        this.goods_id    = goods_id;
        this.goods_count = goods_count;
        this.goods_price = goods_price;
    }

    public long getId() {
        return id;
    }
    public long getDocument_id() {
        return document_id;
    }
    public long getGoods_id() {
        return goods_id;
    }
    public long getGoods_count() {
        return goods_count;
    }
    public long getGoods_price() {
        return goods_price;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("document_id", document_id);
            object.put("goods_id",    goods_id);
            object.put("goods_count", goods_count);
            object.put("goods_price", goods_price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}