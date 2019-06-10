package EntityHandler;

import Database.DAO.DAOAbstract;
import Database.DAO.DAOProvider;
import Entity.*;
import Utility.DateHandler;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class ImportDocumentHandler implements EntityHandler {

    private static ImportDocumentHandler instance = new ImportDocumentHandler();
    private ImportDocumentHandler() {}
    public static ImportDocumentHandler getInstance() {
        return instance;
    }

    @Override
    public JSONObject getJSON(Entity entity, List<String> representativeData) throws ServletException {
        ImportDocument castedEntity = (ImportDocument) entity;
        JSONObject object = new JSONObject();
        try {
            object.put("id",          castedEntity.getId());
            object.put("providerId", "(" + castedEntity.getProviderId() + ") " + representativeData.get(0));
            object.put("importDate",  DateHandler.javaDateToSQLDate(castedEntity.getImportDate()));
            object.put("description", castedEntity.getDescription());
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return object;
    }

    @Override
    public Entity setByJSON(JSONObject json) throws ServletException {
        ImportDocument entity = new ImportDocument();
        try {
            entity.setId(          (!json.has("id")          || json.getString("id").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("id"))));
            entity.setProviderId(  (!json.has("providerId")  || json.getString("providerId").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_LONG : Long.parseLong(json.getString("providerId"))));
            entity.setImportDate(  (!json.has("importDate")  || json.getString("importDate").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_DATE : DateHandler.sqlDateToJavaDate(json.getString("importDate"))));
            entity.setDescription( (!json.has("description") || json.getString("description").equals(Entity.UNDEFINED_STRING) ? Entity.UNDEFINED_STRING : json.getString("description")));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }
        return entity;
    }

    @Override
    public ArrayList<Long> getForeingKeys(Entity entity) {
        ImportDocument castedEntity = (ImportDocument) entity;
        ArrayList<Long> result = new ArrayList<>();
        result.add(castedEntity.getProviderId());
        return result;
    }

    @Override
    public ArrayList<DAOAbstract> getForeingDAO(Entity entity) {
        ArrayList<DAOAbstract> result = new ArrayList<>();
        result.add(DAOProvider.getInstance());
        return result;
    }

    @Override
    public String getRepresentantiveData(Entity entity) {
        return null;
    }
}