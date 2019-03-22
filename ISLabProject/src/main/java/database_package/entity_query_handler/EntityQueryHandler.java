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

    public void GetEntityList(BufferedReader reader, PrintWriter writer) throws IOException {
        DAOAbstract dao = getDAO();
        Entity filter = dao.createEntity();
        try {
            filter.setByJSON(new JSONObject(reader.readLine()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean limited = Boolean.parseBoolean(reader.readLine());
        int begin_index = Integer.parseInt(reader.readLine());
        int count_of_records = Integer.parseInt(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = dao.GetEntityList(connection, filter, limited, begin_index, count_of_records);
        pool.DropConnection(connection);

        JSONArray json_list = new JSONArray();
        for (Entity provider : list) {
            json_list.put(provider.getJSON());
        }

        writer.write(json_list.toString());
    }
    public void AddEntity(BufferedReader reader, PrintWriter writer) throws IOException {
        DAOAbstract dao = getDAO();
        Entity entity = dao.createEntity();
        try {
            entity.setByJSON(new JSONObject(reader.readLine()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
            if (dao.DeleteEntity(connection, id))
                writer.print("ok");
            else
                writer.print("bad");
        } else
            writer.print("not exist");

        pool.DropConnection(connection);
    }
    public void EditEntity(BufferedReader reader, PrintWriter writer) throws IOException {
        DAOAbstract dao = getDAO();
        Entity entity = dao.createEntity();

        try {
            entity.setByJSON(new JSONObject(reader.readLine()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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