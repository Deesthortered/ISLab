package data_model;

public class ImportMoveDocument {
    private long id;
    private long importGoods_id;
    private long storage_id;

    public ImportMoveDocument(long id, long importGoods_id, long storage_id) {
        this.id = id;
        this.importGoods_id = importGoods_id;
        this.storage_id = storage_id;
    }
}
