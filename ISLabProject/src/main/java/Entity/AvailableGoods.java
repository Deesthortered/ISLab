package Entity;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOGoods;
import Database.DAO.DAOProvider;
import Database.DAO.DAOStorage;
import org.json.JSONException;
import org.json.JSONObject;
import Utility.DateHandler;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AvailableGoods implements Entity {
    private long    id;
    private long    goodsId;
    private long    goodsCount;
    private long    providerId;
    private long    storageId;
    private boolean current;
    private Date    snapshotDate;

    public AvailableGoods() {
        this.id           = Entity.UNDEFINED_LONG;
        this.goodsId      = Entity.UNDEFINED_LONG;
        this.goodsCount   = Entity.UNDEFINED_LONG;
        this.providerId   = Entity.UNDEFINED_LONG;
        this.storageId    = Entity.UNDEFINED_LONG;
        this.current      = false;
        this.snapshotDate = Entity.UNDEFINED_DATE;
    }
    public AvailableGoods(long id, long goodsId, long goodsCount, long providerId, long storageId, boolean current, Date snapshotDate) {
        this.id         = id;
        this.goodsId    = goodsId;
        this.goodsCount = goodsCount;
        this.providerId = providerId;
        this.storageId  = storageId;
        this.current    = current;
        if (snapshotDate == null)
            this.snapshotDate = Entity.UNDEFINED_DATE;
        else this.snapshotDate = snapshotDate;
    }

    @Override
    public long    getId() {
        return id;
    }
    public long    getGoodsId() {
        return goodsId;
    }
    public long    getGoodsCount() {
        return goodsCount;
    }
    public long    getProviderId() {
        return providerId;
    }
    public long    getStorageId() {
        return storageId;
    }
    public boolean isCurrent() {
        return current;
    }
    public Date    getSnapshotDate() {
        return snapshotDate;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
    public void setGoodsCount(long goodsCount) {
        this.goodsCount = goodsCount;
    }
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }
    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }
    public void setCurrent(boolean current) {
        this.current = current;
    }
    public void setSnapshotDate(Date snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    @Override
    public JSONObject getJSON(List<String> representativeData) throws ServletException {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("goodsId",    "(" + goodsId + ") " + representativeData.get(0));
            object.put("goodsCount",  goodsCount);
            object.put("providerId", "(" + providerId + ") " + representativeData.get(1));
            object.put("storageId",  "(" + storageId + ") " + representativeData.get(2));
            object.put("current",      current);
            object.put("snapshotDate", DateHandler.javaDateToSQLDate(snapshotDate));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) throws ServletException {
        try {
            this.id           = (!json.has("id")           || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id")));
            this.goodsId      = (!json.has("goodsId")      || json.getString("goodsId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsId")));
            this.goodsCount   = (!json.has("goodsCount")   || json.getString("goodsCount").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsCount")));
            this.providerId   = (!json.has("providerId")   || json.getString("providerId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("providerId")));
            this.storageId    = (!json.has("storageId")    || json.getString("storageId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("storageId")));
            this.current      = (!json.has("current")      ||!json.getString("current").equals(Entity.UNDEFINED_STRING) && json.getBoolean("current"));
            this.snapshotDate = (!json.has("snapshotDate") || json.getString("snapshotDate").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_DATE : DateHandler.sqlDateToJavaDate(json.getString("snapshotDate")));
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
        result.add(DAOGoods.getInstance());
        result.add(DAOProvider.getInstance());
        result.add(DAOStorage.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        ArrayList<Long> result = new ArrayList<>();
        result.add(goodsId);
        result.add(providerId);
        result.add(storageId);
        return result;
    }
}