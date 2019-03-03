package data_model;

import java.util.Date;

public class ExportSummary {
    private long id;
    private Date start_date;
    private Date end_date;
    private int  exports_count;
    private long exports_amount;
    private long max_price;
    private long min_price;

    public ExportSummary(long id, Date start_date, Date end_date, int exports_count, long exports_amount, long max_price, long min_price) {
        this.id = id;
        this.start_date = (Date) start_date.clone();
        this.end_date = (Date) end_date.clone();
        this.exports_count = exports_count;
        this.exports_amount = exports_amount;
        this.max_price = max_price;
        this.min_price = min_price;
    }
}
