package Database.EntityQueryHandler;

import Entity.Entity;
import Database.DAO.DAOAbstract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class EntityQueryHandler {

    public void GetEntityList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        DAOAbstract dao = getDAO();
        Entity filter = dao.createEntity();

        try {
            filter.setByJSON(new JSONObject(reader.readLine()));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }

        boolean limited = Boolean.parseBoolean(reader.readLine());
        int begin_index = Integer.parseInt(reader.readLine());
        int count_of_records = Integer.parseInt(reader.readLine());

        ArrayList<Entity> list;
        try {
            list = dao.GetEntityList(filter, limited, begin_index, count_of_records);
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }

        JSONArray json_list = new JSONArray();
        for (Entity entity : list) {
            ArrayList<String> represantiveData = new ArrayList<>();
            ArrayList<Long> foreingKeys = entity.getForeingKeys();
            ArrayList<DAOAbstract> dao_array = entity.getForeingDAO();
            if (dao_array != null)
            for (int i = 0; i < dao_array.size(); i++) {
                Entity sub_filter = dao_array.get(i).createEntity();
                sub_filter.setId(foreingKeys.get(i));
                Entity sub_entity;
                try {
                    sub_entity = dao_array.get(i).GetEntityList(sub_filter, limited, 0, 1).get(0);
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }
                represantiveData.add(sub_entity.getRepresantiveData());
            }
            json_list.put(entity.getJSON(represantiveData));
        }
        writer.write(json_list.toString());
    }
    public void AddEntity(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        DAOAbstract dao = getDAO();
        Entity entity = dao.createEntity();

        String json_str = reader.readLine();
        JSONObject json;
        try {
            json = new JSONObject(json_str);
        } catch (JSONException e) {
            throw new IOException();
        }
        entity.setByJSON(json);

        ArrayList<Entity> list = new ArrayList<>();
        list.add(entity);
        try {
            if (dao.AddEntityList(list))
                writer.print("ok");
            else
                writer.print("bad");
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
    }
    public void DeleteEntity(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        long id = Long.parseLong(reader.readLine());
        DAOAbstract dao = getDAO();

        try {
            if (dao.IsExistsEntity(id)) {
                Entity filter = dao.createEntity();
                filter.setId(id);
                if (dao.DeleteEntityList(filter))
                    writer.print("ok");
                else
                    writer.print("bad");
            } else
                writer.print("not exist");
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
    }
    public void EditEntity(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        DAOAbstract dao = getDAO();
        Entity entity = dao.createEntity();
        try {
            entity.setByJSON(new JSONObject(reader.readLine()));
        } catch (JSONException e) {
            throw new IOException();
        }

        try {
            if (dao.IsExistsEntity(entity.getId())) {
                if (dao.EditEntity(entity))
                    writer.print("ok");
                else
                    writer.print("bad");
            } else
                writer.print("not exist");
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
    }

    public abstract DAOAbstract getDAO();
}