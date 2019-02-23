package data_model;

import java.util.Date;

public class Import {
    private long id;
    private long provider_id;
    private long storage_id;
    private long goods_id;
    private Date date;
    private long price;

    public Import(long id, long provider_id, long storage_id, long goods_id, Date date, long price) {
        this.id = id;
        this.provider_id = provider_id;
        this.storage_id = storage_id;
        this.goods_id = goods_id;
        this.date = (Date)date.clone();
        this.price = price;
    }
}
