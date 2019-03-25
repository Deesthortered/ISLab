package data_model;

import database_package.dao_package.DAOAbstract;
import org.json.JSONException;
import org.json.JSONObject;
import utility_package.Common;

import java.util.ArrayList;
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

    @Override
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
    public void setId(long id) {
        this.id = id;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
    public void setExports_count(int exports_count) {
        this.exports_count = exports_count;
    }
    public void setExports_amount(long exports_amount) {
        this.exports_amount = exports_amount;
    }
    public void setMax_price(long max_price) {
        this.max_price = max_price;
    }
    public void setMin_price(long min_price) {
        this.min_price = min_price;
    }

    @Override
    public JSONObject getJSON(ArrayList<String> represantive_data) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",             id);
            object.put("start_date",     Common.JavaDateToSQLDate(start_date));
            object.put("end_date",       Common.JavaDateToSQLDate(end_date));
            object.put("exports_count",  exports_count);
            object.put("exports_amount", exports_amount);
            object.put("max_price",      max_price);
            object.put("min_price",      min_price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) {
        try {
            this.id             = (!json.has("id") || json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.start_date     = (!json.has("start_date") || json.getString("start_date").equals(Entity.undefined_string) ? Entity.undefined_date : Common.SQLDateToJavaDate(json.getString("start_date")));
            this.end_date       = (!json.has("end_date") || json.getString("end_date").equals(Entity.undefined_string) ? Entity.undefined_date : Common.SQLDateToJavaDate(json.getString("end_date")));
            this.exports_count  = (!json.has("exports_count") || json.getString("exports_count").equals(Entity.undefined_string) ? Entity.undefined_int : Integer.parseInt(json.getString("exports_count")));
            this.exports_amount = (!json.has("exports_amount") || json.getString("exports_amount").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("exports_amount")));
            this.max_price      = (!json.has("max_price") || json.getString("max_price").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("max_price")));
            this.min_price      = (!json.has("min_price") || json.getString("min_price").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("min_price")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRepresantiveData() {
        return null;
    }
    @Override
    public ArrayList<DAOAbstract> getForeingDAO() {
        return null;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        return null;
    }
}