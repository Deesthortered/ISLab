package data_model;

public class AvailableGoods {
    private long id;
    private long goods_id;
    private long provider_id;
    private long storage_id;

    public AvailableGoods(long id, long goods_id, long provider_id, long storage_id) {
        this.id = id;
        this.goods_id = goods_id;
        this.provider_id = provider_id;
        this.storage_id = storage_id;
    }
}
