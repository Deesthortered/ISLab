package data_model;

import java.util.Date;

public class Export {
    private long id;
    private long customer_id;
    private long storage_id;
    private long goods_id;
    private Date date;
    private long price;

    public Export(long id, long customer_id, long storage_id, long goods_id, Date date, long price) {
        this.id = id;
        this.customer_id = customer_id;
        this.storage_id = storage_id;
        this.goods_id = goods_id;
        this.date = (Date) date.clone();
        this.price = price;
    }
}
