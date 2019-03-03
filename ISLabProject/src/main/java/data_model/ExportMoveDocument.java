package data_model;

public class ExportMoveDocument {
    private long id;
    private long exportGoods_id;
    private long storage_id;

    public ExportMoveDocument(long id, long exportGoods_id, long storage_id) {
        this.id = id;
        this.exportGoods_id = exportGoods_id;
        this.storage_id = storage_id;
    }
}
