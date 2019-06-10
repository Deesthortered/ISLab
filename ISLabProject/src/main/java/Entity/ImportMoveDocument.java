package Entity;

public class ImportMoveDocument implements Entity {
    private long id;
    private long importGoodsId;
    private long storageId;

    public ImportMoveDocument() {
        this.id            = Entity.UNDEFINED_LONG;
        this.importGoodsId = Entity.UNDEFINED_LONG;
        this.storageId     = Entity.UNDEFINED_LONG;
    }
    public ImportMoveDocument(long id, long importGoodsId, long storageId) {
        this.id            = id;
        this.importGoodsId = importGoodsId;
        this.storageId     = storageId;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getImportGoodsId() {
        return importGoodsId;
    }
    public long getStorageId() {
        return storageId;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setImportGoodsId(long importGoodsId) {
        this.importGoodsId = importGoodsId;
    }
    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }
}