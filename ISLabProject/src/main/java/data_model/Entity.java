package data_model;

import database_package.dao_package.DAOAbstract;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public interface Entity {
    int    undefined_int    = -1;
    long   undefined_long   = -1;
    String undefined_string = "____undefined____";
    Date   undefined_date   = new Date(Long.MIN_VALUE);

    long getId();
    void setId(long id);

    JSONObject getJSON(ArrayList<String> represantive_data);
    void setByJSON(JSONObject json);

    String getRepresantiveData();
    ArrayList<DAOAbstract> getForeingDAO();
    ArrayList<Long> getForeingKeys();
}