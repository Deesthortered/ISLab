package data_model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class ExportSummary implements Entity {
    private long id;
    private Date start_date;
    private Date end_date;
    private int  exports_count;
    private long exports_amount;
    private long max_price;
    private long min_price;

    public ExportSummary() {
        this.id             = Entity.undefined_long;
        this.start_date     = Entity.undefined_date;
        this.end_date       = Entity.undefined_date;
        this.exports_count  = Entity.undefined_int;
        this.exports_amount = Entity.undefined_long;
        this.max_price      = Entity.undefined_long;
        this.min_price      = Entity.undefined_long;
    }
    public ExportSummary(long id, Date start_date, Date end_date, int exports_count, long exports_amount, long max_price, long min_price) {
        this.id = id;

        if (start_date == null)
            start_date = Entity.undefined_date;
        else this.start_date = (Date) start_date.clone();

        if (end_date == null)
            end_date = Entity.undefined_date;
        else this.end_date = (Date) end_date.clone();

        this.exports_count  = exports_count;
        this.exports_amount = exports_amount;
        this.max_price      = max_price;
        this.min_price      = min_price;
    }

    public long getId() {
        return id;
    }
    public Date getStart_date() {
        return start_date;
    }
    public Date getEnd_date() {
        return end_date;
    }
    public int  getExports_count() {
        return exports_count;
    }
    public long getExports_amount() {
        return exports_amount;
    }
    public long getMax_price() {
        return max_price;
    }
    public long getMin_price() {
        return min_price;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",             id);
            object.put("start_date",     start_date.toString());
            object.put("end_date",       end_date.toString());
            object.put("exports_count",  exports_count);
            object.put("exports_amount", exports_amount);
            object.put("max_price",      max_price);
            object.put("min_price",      min_price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}