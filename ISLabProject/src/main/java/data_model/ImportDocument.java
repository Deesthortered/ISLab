package data_model;

import org.json.JSONException;
import org.json.JSONObject;
import utility_package.Common;

import java.util.Date;

public class ImportDocument implements Entity {
    private long   id;
    private long   provider_id;
    private Date   import_date;
    private String description;

    public ImportDocument() {
        this.id          = Entity.undefined_long;
        this.provider_id = Entity.undefined_long;
        this.import_date = Entity.undefined_date;
        this.description = Entity.undefined_string;
    }
    public ImportDocument(long id, long provider_id, Date import_date, String description) {
        this.id = id;
        this.provider_id = provider_id;
        this.import_date = import_date;
        if (description == null)
            this.description = null;
        else this.description = description;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getProvider_id() {
        return provider_id;
    }
    public Date getImport_date() {
        return import_date;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setProvider_id(long provider_id) {
        this.provider_id = provider_id;
    }
    public void setImport_date(Date import_date) {
        this.import_date = import_date;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("provider_id", provider_id);
            object.put("import_date", Common.JavaDateToSQLDate(import_date));
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
            this.provider_id = (json.getString("provider_id").equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(json.getString("provider_id")));
            this.import_date = (json.getString("import_date").equals(Entity.undefined_string) ? Entity.undefined_date : Common.SQLDateToJavaDate(json.getString("import_date")));
            this.description = (json.getString("description").equals(Entity.undefined_string) ? Entity.undefined_string : json.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getParametrizedJSON(String provider) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("provider_id", (provider == null ? provider_id : provider) );
            object.put("import_date", Common.JavaDateToSQLDate(import_date));
            object.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
