package Entity;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOCustomer;
import org.json.JSONException;
import org.json.JSONObject;
import Utility.DateHandler;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExportDocument implements Entity {
    private long   id;
    private long   customerId;
    private Date   exportDate;
    private String description;

    public ExportDocument() {
        this.id          = Entity.UNDEFINED_LONG;
        this.customerId  = Entity.UNDEFINED_LONG;
        this.exportDate  = Entity.UNDEFINED_DATE;
        this.description = Entity.UNDEFINED_STRING;
    }
    public ExportDocument(long id, long customerId, Date exportDate, String description) {
        this.id = id;
        this.customerId = customerId;
        if (exportDate == null)
            this.exportDate = Entity.UNDEFINED_DATE;
        else this.exportDate = exportDate;
        if (description == null)
            this.description = Entity.UNDEFINED_STRING;
        else this.description = description;
    }

    @Override
    public long   getId() {
        return id;
    }
    public long   getCustomerId() {
        return customerId;
    }
    public Date   getExportDate() {
        return exportDate;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject getJSON(List<String> representativeData) throws ServletException {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("customerId", "(" + customerId + ") " + representativeData.get(0));
            object.put("exportDate",  DateHandler.javaDateToSQLDate(exportDate));
            object.put("description", description);
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }
    @Override
    public void setByJSON(JSONObject json) throws ServletException {
        try {
            this.id          = (!json.has("id")          || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id")));
            this.customerId  = (!json.has("customerId")  || json.getString("customerId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("customerId")));
            this.exportDate  = (!json.has("exportDate")  || json.getString("exportDate").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_DATE : DateHandler.sqlDateToJavaDate(json.getString("exportDate")));
            this.description = (!json.has("description") || json.getString("description").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("description"));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    public String getRepresentantiveData() {
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
        result.add(customerId);
        return result;
    }
}