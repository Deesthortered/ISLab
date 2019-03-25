package data_model;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOGoods;
import database_package.dao_package.DAOProvider;
import database_package.dao_package.DAOStorage;
import org.json.JSONException;
import org.json.JSONObject;
import utility_package.Common;

import java.util.ArrayList;
import java.util.Date;

public class AvailableGoods implements Entity {
    private long id;
    private long goods_id;
    private long goods_count;
    private long provider_id;
    private long storage_id;
    private boolean current;
    private Date snapshot_date;

    public AvailableGoods() {
        this.id          = Entity.undefined_long;
        this.goods_id    = Entity.undefined_long;
        this.goods_count = Entity.undefined_long;
        this.provider_id = Entity.undefined_long;
        this.storage_id  = Entity.undefined_long;
        this.current     = false;
        this.snapshot_date = Entity.undefined_date;
    }
    public AvailableGoods(long id, long goods_id, long goods_count, long provider_id, long storage_id, boolean current, Date snapshot_date) {
        this.id          = id;
        this.goods_id    = goods_id;
        this.goods_count = goods_count;
        this.provider_id = provider_id;
        this.storage_id  = storage_id;
        this.current     = current;
        if (snapshot_date == null)
            this.snapshot_date = Entity.undefined_date;
        else this.snapshot_date = snapshot_date;
    }

    @Override
    public long    getId() {
        return id;
    }
    public long    getGoods_id() {
        return goods_id;
    }
    public long    getGoods_count() {
        return goods_count;
    }
    public long    getProvider_id() {
        return provider_id;
    }
    public long    getStorage_id() {
        return storage_id;
    }
    public boolean isCurrent() {
        return current;
    }
    public Date    getSnapshot_date() {
        return snapshot_date;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }
    public void setGoods_count(long goods_count) {
        this.goods_count = goods_count;
    }
    public void setProvider_id(long provider_id) {
        this.provider_id = provider_id;
    }
    public void setStorage_id(long storage_id) {
        this.storage_id = storage_id;
    }
    public void setCurrent(boolean current) {
        this.current = current;
    }
    public void setSnapshot_date(Date snapshot_date) {
        this.snapshot_date = snapshot_date;
    }

    @Override
    public JSONObject getJSON(ArrayList<String> represantive_data) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("goods_id",    "(" + goods_id + ") " + represantive_data.get(0));
            object.put("goods_count", "(" + goods_count + ") " + represantive_data.get(1));
            object.put("provider_id", "(" + provider_id + ") " + represantive_data.get(2));
            object.put("storage_id",  "(" + storage_id + ") " + represantive_data.get(3));
            object.put("current",     current);
            object.put("snapshot_date", Common.JavaDateToSQLDate(snapshot_date));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) {
        try {
            this.id            = (!json.has("id") || json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.goods_id      = (!json.has("goods_id") || json.getString("goods_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("goods_id")));
            this.goods_count   = (!json.has("goods_count") || json.getString("goods_count").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("goods_count")));
            this.provider_id   = (!json.has("provider_id") || json.getString("provider_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("provider_id")));
            this.storage_id    = (!json.has("storage_id") || json.getString("storage_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("storage_id")));
            this.current       = (!json.has("current") || !json.getString("current").equals(Entity.undefined_string) && json.getBoolean("current"));
            this.snapshot_date = (!json.has("snapshot_date") || json.getString("snapshot_date").equals(Entity.undefined_string) ? Entity.undefined_date : Common.SQLDateToJavaDate(json.getString("snapshot_date")));
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
        result.add(DAOGoods.getInstance());
        result.add(DAOProvider.getInstance());
        result.add(DAOStorage.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        ArrayList<Long> result = new ArrayList<>();
        result.add(goods_id);
        result.add(provider_id);
        result.add(storage_id);
        return result;
    }
}