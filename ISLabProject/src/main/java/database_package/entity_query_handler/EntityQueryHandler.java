package database_package.entity_query_handler;

import data_model.Entity;
import database_package.ConnectionPool;
import database_package.dao_package.DAOAbstract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

public abstract class EntityQueryHandler {

    public void GetEntityList(BufferedReader reader, PrintWriter writer) throws IOException, JSONException {
        DAOAbstract dao = getDAO();
        Entity filter = dao.createEntity();
        filter.setByJSON(new JSONObject(reader.readLine()));

        boolean limited = Boolean.parseBoolean(reader.readLine());
        int begin_index = Integer.parseInt(reader.readLine());
        int count_of_records = Integer.parseInt(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = dao.GetEntityList(connection, filter, limited, begin_index, count_of_records);

        JSONArray json_list = new JSONArray();
        for (Entity entity : list) {
            ArrayList<String> represantiveData = new ArrayList<>();
            ArrayList<Long> foreingKeys = entity.getForeingKeys();
            ArrayList<DAOAbstract> dao_array = entity.getForeingDAO();
            if (dao_array != null)
            for (int i = 0; i < dao_array.size(); i++) {
                Entity sub_filter = dao_array.get(i).createEntity();
                sub_filter.setId(foreingKeys.get(i));
                Entity sub_entity = dao_array.get(i).GetEntityList(connection, sub_filter, limited, 0, 1).get(0);
                represantiveData.add(sub_entity.getRepresantiveData());
            }
            json_list.put(entity.getJSON(represantiveData));
        }
        pool.DropConnection(connection);
        writer.write(json_list.toString());
    }
    public void AddEntity(BufferedReader reader, PrintWriter writer) throws IOException, JSONException {
        DAOAbstract dao = getDAO();
        Entity entity = dao.createEntity();

        String json_str = reader.readLine();
        JSONObject json = new JSONObject(json_str);
        entity.setByJSON(json);

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = new ArrayList<>();
        list.add(entity);
        if (dao.AddEntityList(connection, list))
            writer.print("ok");
        else
            writer.print("bad");
        pool.DropConnection(connection);
    }
    public void DeleteEntity(BufferedReader reader, PrintWriter writer) throws IOException {
        long id = Long.parseLong(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOAbstract dao = getDAO();

        Connection connection = pool.GetConnection();
        if (dao.IsExistsEntity(connection, id)) {
            Entity filter = dao.createEntity();
            filter.setId(id);
            if (dao.DeleteEntityList(connection, filter))
                writer.print("ok");
            else
                writer.print("bad");
        } else
            writer.print("not exist");

        pool.DropConnection(connection);
    }
    public void EditEntity(BufferedReader reader, PrintWriter writer) throws IOException, JSONException {
        DAOAbstract dao = getDAO();
        Entity entity = dao.createEntity();
        entity.setByJSON(new JSONObject(reader.readLine()));

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        if (dao.IsExistsEntity(connection, entity.getId())) {
            if (dao.EditEntity(connection, entity))
                writer.print("ok");
            else
                writer.print("bad");
        } else
            writer.print("not exist");
        pool.DropConnection(connection);
    }

    public abstract DAOAbstract getDAO();
}