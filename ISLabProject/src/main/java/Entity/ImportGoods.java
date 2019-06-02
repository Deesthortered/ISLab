package Entity;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOGoods;
import Database.DAO.DAOImportDocument;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImportGoods implements Entity {
    private long id;
    private long document_id;
    private long goods_id;
    private long goods_count;
    private long goods_price;

    public ImportGoods() {
        this.id          = Entity.undefined_long;
        this.document_id = Entity.undefined_long;
        this.goods_id    = Entity.undefined_long;
        this.goods_count = Entity.undefined_long;
        this.goods_price = Entity.undefined_long;
    }
    public ImportGoods(long id, long document_id, long goods_id, long goods_count, long goods_price) {
        this.id          = id;
        this.document_id = document_id;
        this.goods_id    = goods_id;
        this.goods_count = goods_count;
        this.goods_price = goods_price;
    }

    @Override
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
    public void setId(long id) {
        this.id = id;
    }
    public void setDocument_id(long document_id) {
        this.document_id = document_id;
    }
    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }
    public void setGoods_count(long goods_count) {
        this.goods_count = goods_count;
    }
    public void setGoods_price(long goods_price) {
        this.goods_price = goods_price;
    }

    @Override
    public JSONObject getJSON(ArrayList<String> represantive_data) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("document_id", "(" + document_id + ") " + represantive_data.get(0));
            object.put("goods_id",   "(" + goods_id + ") " +  represantive_data.get(1));
            object.put("goods_count", goods_count);
            object.put("goods_price", goods_price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) {
        try {
            this.id          = (!json.has("id") || json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.document_id = (!json.has("document_id") || json.getString("document_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("document_id")));
            this.goods_id    = (!json.has("goods_id") || json.getString("goods_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("goods_id")));
            this.goods_count = (!json.has("goods_count") || json.getString("goods_count").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("goods_count")));
            this.goods_price = (!json.has("goods_price") || json.getString("goods_price").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("goods_price")));
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
        result.add(DAOImportDocument.getInstance());
        result.add(DAOGoods.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        ArrayList<Long> result = new ArrayList<>();
        result.add(document_id);
        result.add(goods_id);
        return result;
    }
}