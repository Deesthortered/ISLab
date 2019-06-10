package Entity;

import java.util.Date;

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
}