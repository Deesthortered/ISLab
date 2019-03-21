package data_model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public interface Entity {
    int    undefined_int    = -1;
    long   undefined_long   = -1;
    String undefined_string = "____undefined____";
    Date   undefined_date   = new Date(Long.MIN_VALUE);

    JSONObject getJSON();
    void setByJSON(JSONObject json);
}