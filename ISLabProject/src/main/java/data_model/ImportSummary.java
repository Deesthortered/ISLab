package data_model;

import java.util.Date;

public class ImportSummary {
    private long id;
    private Date start_date;
    private Date end_date;
    private int  imports_count;
    private long imports_amount;
    private long max_price;
    private long min_price;

    public ImportSummary(long id, Date start_date, Date end_date, int imports_count, long imports_amount, long max_price, long min_price) {
        this.id = id;
        this.start_date = (Date) start_date.clone();
        this.end_date = (Date) end_date.clone();
        this.imports_count = imports_count;
        this.imports_amount = imports_amount;
        this.max_price = max_price;
        this.min_price = min_price;
    }
}
