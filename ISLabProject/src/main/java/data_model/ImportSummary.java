package data_model;

import database_package.dao_package.DAOAbstract;
import org.json.JSONException;
import org.json.JSONObject;
import utility_package.DateHandler;

import java.util.ArrayList;
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
    public void setId(long id) {
        this.id = id;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
    public void setImports_count(int imports_count) {
        this.imports_count = imports_count;
    }
    public void setImports_amount(long imports_amount) {
        this.imports_amount = imports_amount;
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
            object.put("start_date",     DateHandler.JavaDateToSQLDate(start_date));
            object.put("end_date",       DateHandler.JavaDateToSQLDate(end_date));
            object.put("imports_count",  imports_count);
            object.put("imports_amount", imports_amount);
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
            this.start_date     = (!json.has("start_date") || json.getString("start_date").equals(Entity.undefined_string) ? Entity.undefined_date : DateHandler.SQLDateToJavaDate(json.getString("start_date")));
            this.end_date       = (!json.has("end_date") || json.getString("end_date").equals(Entity.undefined_string) ? Entity.undefined_date : DateHandler.SQLDateToJavaDate(json.getString("end_date")));
            this.imports_count  = (!json.has("imports_count") || json.getString("imports_count").equals(Entity.undefined_string) ? Entity.undefined_int : Integer.parseInt(json.getString("imports_count")));
            this.imports_amount = (!json.has("exports_amount") || json.getString("exports_amount").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("imports_amount")));
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
