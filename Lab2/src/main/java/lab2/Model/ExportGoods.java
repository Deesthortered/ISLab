package lab2.Model;

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
}