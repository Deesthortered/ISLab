package data_model;

import org.json.JSONException;
import org.json.JSONObject;
import utility_package.Common;

import java.util.Date;

public class ExportDocument implements Entity {
    private long   id;
    private long   customer_id;
    private Date   export_date;
    private String description;

    public ExportDocument() {
        this.id          = Entity.undefined_long;
        this.customer_id = Entity.undefined_long;
        this.export_date = Entity.undefined_date;
        this.description = Entity.undefined_string;
    }
    public ExportDocument(long id, long customer_id, Date export_date, String description) {
        this.id = id;
        this.customer_id = customer_id;
        if (export_date == null)
            this.export_date = Entity.undefined_date;
        else this.export_date = export_date;
        if (description == null)
            this.description = Entity.undefined_string;
        else this.description = description;
    }

    public long getId() {
        return id;
    }
    public long getCustomer_id() {
        return customer_id;
    }
    public Date getExport_date() {
        return export_date;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("customer_id", customer_id);
            object.put("export_date", Common.JavaDateToSQLDate(export_date));
            object.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) {
        try {
            this.id          = (json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.customer_id = (json.getString("customer_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("customer_id")));
            this.export_date = (json.getString("export_date").equals(Entity.undefined_string) ? Entity.undefined_date : Common.SQLDateToJavaDate(json.getString("export_date")));
            this.description = (json.getString("description").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getParametrizedJSON(String customer) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("customer_id", (customer == null ? customer_id : customer));
            object.put("export_date", Common.JavaDateToSQLDate(export_date));
            object.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}