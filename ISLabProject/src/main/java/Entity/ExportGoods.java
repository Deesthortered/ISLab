package Entity;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOExportDocument;
import Database.DAO.DAOGoods;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class ExportGoods implements Entity {
    private long id;
    private long documentId;
    private long goodsId;
    private long goodsCount;
    private long goodsPrice;

    public ExportGoods() {
        this.id         = Entity.UNDEFINED_LONG;
        this.documentId = Entity.UNDEFINED_LONG;
        this.goodsId    = Entity.UNDEFINED_LONG;
        this.goodsCount = Entity.UNDEFINED_LONG;
        this.goodsPrice = Entity.UNDEFINED_LONG;
    }
    public ExportGoods(long id, long documentId, long goodsId, long goodsCount, long goodsPrice) {
        this.id         = id;
        this.documentId = documentId;
        this.goodsId    = goodsId;
        this.goodsCount = goodsCount;
        this.goodsPrice = goodsPrice;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getDocumentId() {
        return documentId;
    }
    public long getGoodsId() {
        return goodsId;
    }
    public long getGoodsCount() {
        return goodsCount;
    }
    public long getGoodsPrice() {
        return goodsPrice;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }
    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
    public void setGoodsCount(long goodsCount) {
        this.goodsCount = goodsCount;
    }
    public void setGoodsPrice(long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public JSONObject getJSON(List<String> representativeData) throws ServletException {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("documentId", "(" + documentId + ") " + representativeData.get(0));
            object.put("goodsId",    "(" + goodsId + ") " + representativeData.get(1));
            object.put("goodsCount", goodsCount);
            object.put("goodsPrice", goodsPrice);
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) throws ServletException {
        try {
            this.id         = (!json.has("id")         || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id")));
            this.documentId = (!json.has("documentId") || json.getString("documentId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("documentId")));
            this.goodsId    = (!json.has("goodsId")    || json.getString("goodsId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsId")));
            this.goodsCount = (!json.has("goodsCount") || json.getString("goodsCount").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsCount")));
            this.goodsPrice = (!json.has("goodsPrice") || json.getString("goodsPrice").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("goodsPrice")));
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
        result.add(DAOExportDocument.getInstance());
        result.add(DAOGoods.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        ArrayList<Long> result = new ArrayList<>();
        result.add(documentId);
        result.add(goodsId);
        return result;
    }
}