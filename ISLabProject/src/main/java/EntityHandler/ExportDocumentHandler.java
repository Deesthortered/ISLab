package EntityHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOCustomer;
import Entity.*;
import Utility.DateHandler;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class ExportDocumentHandler implements EntityHandler {

    private static ExportDocumentHandler instance = new ExportDocumentHandler();
    private ExportDocumentHandler() {}
    public static ExportDocumentHandler getInstance() {
        return instance;
    }

    @Override
    public JSONObject getJSON(Entity entity, List<String> representativeData) throws ServletException {
        ExportDocument castedEntity = (ExportDocument) entity;
        JSONObject object = new JSONObject();
        try {
            object.put("id",          castedEntity.getId());
            object.put("customerId", "(" + castedEntity.getCustomerId() + ") " + representativeData.get(0));
            object.put("exportDate",  DateHandler.javaDateToSQLDate(castedEntity.getExportDate()));
            object.put("description", castedEntity.getDescription());
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }

    @Override
    public Entity setByJSON(JSONObject json) throws ServletException {
        ExportDocument entity = new ExportDocument();
        try {
            entity.setId(          (!json.has("id")          || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id"))));
            entity.setCustomerId(  (!json.has("customerId")  || json.getString("customerId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("customerId"))));
            entity.setExportDate(  (!json.has("exportDate")  || json.getString("exportDate").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_DATE : DateHandler.sqlDateToJavaDate(json.getString("exportDate"))));
            entity.setDescription( (!json.has("description") || json.getString("description").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("description")));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return entity;
    }

    @Override
    public ArrayList<Long> getForeingKeys(Entity entity) {
        ExportDocument castedEntity = (ExportDocument) entity;
        ArrayList<Long> result = new ArrayList<>();
        result.add(castedEntity.getCustomerId());
        return result;
    }

    @Override
    public ArrayList<DAOAbstract> getForeingDAO(Entity entity) {
        ArrayList<DAOAbstract> result = new ArrayList<>();
        result.add(DAOCustomer.getInstance());
        return result;
    }

    @Override
    public String getRepresentantiveData(Entity entity) {
        return null;
    }
}
