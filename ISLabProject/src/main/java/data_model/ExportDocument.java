package data_model;

import database_package.dao_package.DAOAbstract;
import database_package.dao_package.DAOCustomer;
import org.json.JSONException;
import org.json.JSONObject;
import utility_package.DateHandler;

import java.util.ArrayList;
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

    @Override
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
    public void setId(long id) {
        this.id = id;
    }
    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }
    public void setExport_date(Date export_date) {
        this.export_date = export_date;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject getJSON(ArrayList<String> represantive_data) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("customer_id", "(" + customer_id + ") " + represantive_data.get(0));
            object.put("export_date", DateHandler.JavaDateToSQLDate(export_date));
            object.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) {
        try {
            this.id          = (!json.has("id") || json.getString("id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("id")));
            this.customer_id = (!json.has("customer_id") || json.getString("customer_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("customer_id")));
            this.export_date = (!json.has("export_date") || json.getString("export_date").equals(Entity.undefined_string) ? Entity.undefined_date : DateHandler.SQLDateToJavaDate(json.getString("export_date")));
            this.description = (!json.has("description") || json.getString("description").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("description"));
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
        ArrayList<DAOAbstract> result = new ArrayList<>();
        result.add(DAOCustomer.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        ArrayList<Long> result = new ArrayList<>();
        result.add(customer_id);
        return result;
    }
}