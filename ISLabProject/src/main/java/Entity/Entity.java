package Entity;

import Database.DAO.DAOAbstract;
import org.json.JSONObject;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface Entity {
    int    UNDEFINED_INT    = -1;
    long   UNDEFINED_LONG   = -1;
    String UNDEFINED_STRING = "____undefined____";
    Date   UNDEFINED_DATE   = new Date(Long.MIN_VALUE);

    long getId();
    void setId(long id);

    JSONObject getJSON(List<String> representativeData) throws ServletException;
    void setByJSON(JSONObject json) throws ServletException;

    String getRepresentantiveData();
    ArrayList<DAOAbstract> getForeingDAO();
    ArrayList<Long> getForeingKeys();
}