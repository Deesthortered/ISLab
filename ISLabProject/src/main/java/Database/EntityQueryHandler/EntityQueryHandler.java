package Database.EntityQueryHandler;

import Database.DAO.*;
import Entity.Entity;
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
import java.util.List;

public abstract class EntityQueryHandler {

    DAOAbstract daoAvailable          = DAOAvailableGoods.getInstance();
    DAOAbstract daoCustomer           = DAOCustomer.getInstance();
    DAOAbstract daoProvider           = DAOProvider.getInstance();
    DAOAbstract daoGoods              = DAOGoods.getInstance();
    DAOAbstract daoStorage            = DAOStorage.getInstance();
    DAOAbstract daoImportDocuments    = DAOImportDocument.getInstance();
    DAOAbstract daoImportGoods        = DAOImportGoods.getInstance();
    DAOAbstract daoImportMoveDocument = DAOImportMoveDocument.getInstance();
    DAOAbstract daoExportDocument     = DAOExportDocument.getInstance();
    DAOAbstract daoExportGoods        = DAOExportGoods.getInstance();
    DAOAbstract daoExportMoveDocument = DAOExportMoveDocument.getInstance();

    protected EntityQueryHandler() {

    }

    public static EntityQueryHandler getInstance() {
        return null;
    }

    public void getEntityList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();

        DAOAbstract dao = getDAO();
        Entity filteringEntity = dao.createEntity();

        try {
            filteringEntity.setByJSON(new JSONObject(request.getHeader("filter")));
        } catch (JSONException e) {
            throw new ServletException(e.getMessage());
        }

        boolean limited = Boolean.parseBoolean((String) request.getAttribute("limited"));
        int beginIndex = Integer.parseInt((String) request.getAttribute("listBeginInd"));
        int countOfRecords = Integer.parseInt((String) request.getAttribute("listSize"));

        List<Entity> list;
        try {
            list = dao.getEntityList(filteringEntity, limited, beginIndex, countOfRecords);
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }

        JSONArray json_list = new JSONArray();
        for (Entity entity : list) {
            List<String> represantiveData = new ArrayList<>();
            List<Long> foreingKeys = entity.getForeingKeys();
            List<DAOAbstract> dao_array = entity.getForeingDAO();
            if (dao_array != null)
            for (int i = 0; i < dao_array.size(); i++) {
                Entity sub_filter = dao_array.get(i).createEntity();
                sub_filter.setId(foreingKeys.get(i));
                Entity sub_entity;
                try {
                    sub_entity = dao_array.get(i).getEntityList(sub_filter, limited, 0, 1).get(0);
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }
                represantiveData.add(sub_entity.getRepresentantiveData());
            }
            json_list.put(entity.getJSON(represantiveData));
        }
        writer.write(json_list.toString());
    }
    public void addEntity(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

        List<Entity> list = new ArrayList<>();
        list.add(entity);
        try {
            if (dao.addEntityList(list))
                writer.print("ok");
            else
                writer.print("bad");
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
    }
    public void deleteEntity(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        long id = Long.parseLong(reader.readLine());
        DAOAbstract dao = getDAO();

        try {
            if (dao.isExistsEntity(id)) {
                Entity filteringEntity = dao.createEntity();
                filteringEntity.setId(id);
                if (dao.deleteEntityList(filteringEntity))
                    writer.print("ok");
                else
                    writer.print("bad");
            } else
                writer.print("not exist");
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
    }
    public void editEntity(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
            if (dao.isExistsEntity(entity.getId())) {
                if (dao.editEntity(entity))
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