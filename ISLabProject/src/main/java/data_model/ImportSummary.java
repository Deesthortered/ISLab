package data_model;

import java.util.Date;

public class ImportSummary {
    private long id;
    private Date start_date;
    private Date end_date;
    private int  import_amount;
    private long summary_price;
    private long max_price;
    private long min_price;

    public ImportSummary(long id, Date start_date, Date end_date, int import_amount, long summary_price, long max_price, long min_price) {
        this.id = id;
        this.start_date = (Date) start_date.clone();
        this.end_date = (Date) end_date.clone();
        this.import_amount = import_amount;
        this.summary_price = summary_price;
        this.max_price = max_price;
        this.min_price = min_price;
    }
}
