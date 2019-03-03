package data_model;

public class ExportGoods {
    private long id;
    private long document_id;
    private long goods_id;
    private long goods_count;
    private long goods_price;

    public ExportGoods(long id, long document_id, long goods_id, long goods_count, long goods_price) {
        this.id = id;
        this.document_id = document_id;
        this.goods_id = goods_id;
        this.goods_count = goods_count;
        this.goods_price = goods_price;
    }
}