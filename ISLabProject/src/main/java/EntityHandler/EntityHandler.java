package EntityHandler;

import Database.DAO.DAOAbstract;
import Entity.*;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public interface EntityHandler {
    JSONObject getJSON(Entity entity, List<String> representativeData) throws ServletException;
    Entity setByJSON(JSONObject json) throws ServletException;

    String getRepresentantiveData(Entity entity);
    ArrayList<DAOAbstract> getForeingDAO(Entity entity);
    ArrayList<Long> getForeingKeys(Entity entity);
}