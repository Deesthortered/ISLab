package data_model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class ImportSummary implements Entity {
    private long id;
    private Date start_date;
    private Date end_date;
    private int  imports_count;
    private long imports_amount;
    private long max_price;
    private long min_price;

    public ImportSummary() {
        this.id             = Entity.undefined_long;
        this.start_date     = Entity.undefined_date;
        this.end_date       = Entity.undefined_date;
        this.imports_count  = Entity.undefined_int;
        this.imports_amount = Entity.undefined_long;
        this.max_price      = Entity.undefined_long;
        this.min_price      = Entity.undefined_long;
    }
    public ImportSummary(long id, Date start_date, Date end_date, int imports_count, long imports_amount, long max_price, long min_price) {
        this.id             = id;
        this.start_date     = (Date) start_date.clone();
        this.end_date       = (Date) end_date.clone();
        this.imports_count  = imports_count;
        this.imports_amount = imports_amount;
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
    public int  getImports_count() {
        return imports_count;
    }
    public long getImports_amount() {
        return imports_amount;
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
            object.put("imports_count",  imports_count);
            object.put("imports_amount", imports_amount);
            object.put("max_price",      max_price);
            object.put("min_price",      min_price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
