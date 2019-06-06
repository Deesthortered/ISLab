package Entity;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOProvider;
import org.json.JSONException;
import org.json.JSONObject;
import Utility.DateHandler;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportDocument implements Entity {
    private long   id;
    private long   providerId;
    private Date   importDate;
    private String description;

    public ImportDocument() {
        this.id          = Entity.UNDEFINED_LONG;
        this.providerId  = Entity.UNDEFINED_LONG;
        this.importDate  = Entity.UNDEFINED_DATE;
        this.description = Entity.UNDEFINED_STRING;
    }
    public ImportDocument(long id, long providerId, Date importDate, String description) {
        this.id = id;
        this.providerId = providerId;
        this.importDate = importDate;
        if (description == null)
            this.description = null;
        else this.description = description;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getProviderId() {
        return providerId;
    }
    public Date getImportDate() {
        return importDate;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject getJSON(List<String> representativeData) throws ServletException {
        JSONObject object = new JSONObject();
        try {
            object.put("id",          id);
            object.put("providerId", "(" + providerId + ") " + representativeData.get(0));
            object.put("importDate",  DateHandler.javaDateToSQLDate(importDate));
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
            this.providerId  = (!json.has("providerId")  || json.getString("providerId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("providerId")));
            this.importDate  = (!json.has("importDate")  || json.getString("importDate").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_DATE : DateHandler.sqlDateToJavaDate(json.getString("importDate")));
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
        result.add(DAOProvider.getInstance());
        return result;
    }
    @Override
    public ArrayList<Long> getForeingKeys() {
        ArrayList<Long> result = new ArrayList<>();
        result.add(providerId);
        return result;
    }
}
