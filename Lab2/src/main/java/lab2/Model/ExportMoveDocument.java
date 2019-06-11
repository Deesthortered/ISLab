package lab2.Model;

public class ExportMoveDocument implements Entity {
    private long id;
    private long exportGoodsId;
    private long storageId;

    public ExportMoveDocument() {
        this.id             = Entity.UNDEFINED_LONG;
        this.exportGoodsId = Entity.UNDEFINED_LONG;
        this.storageId = Entity.UNDEFINED_LONG;
    }
    public ExportMoveDocument(long id, long exportGoodsId, long storageId) {
        this.id             = id;
        this.exportGoodsId = exportGoodsId;
        this.storageId = storageId;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getExportGoodsId() {
        return exportGoodsId;
    }
    public long getStorageId() {
        return storageId;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setExportGoodsId(long exportGoodsId) {
        this.exportGoodsId = exportGoodsId;
    }
    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }
}