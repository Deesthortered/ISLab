package main_package;

import data_model.AvailableGoods;
import data_model.Entity;
import data_model.ExportGoods;
import data_model.ImportGoods;
import database_package.ConnectionPool;
import database_package.dao_package.*;
import database_package.entity_query_handler.*;
import org.json.JSONException;
import utility_package.Common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UserHandlerServlet")
public class UserHandlerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        String title = reader.readLine();

        switch (title) {
            case Common.q_get_role:
                GetRole(request, response);
                break;
            case Common.q_entity_query:
                try {
                    HandleEntityQuery(reader, writer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Common.q_rebuild_database:
                //RebuildDatabase(request, response);
                break;
            default:
                SendError(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Logout(request, response))
            return;
        HttpSession session = request.getSession();
        String logged = (String) session.getAttribute(Common.atr_logged);

        if (logged.equals(Common.str_true)) {
            String role = (String) session.getAttribute(Common.atr_role);
            request.setAttribute(Common.atr_role, role);
            request.getRequestDispatcher(Common.html_menu).forward(request, response);
        } else {
            PrintWriter writer = response.getWriter();
            writer.println("ERROR");
        }
    }

    private void SendError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        reader.reset();
        String error = "ERROR: Client query title = " + reader.readLine();
        writer.println(error);
        System.out.println(error);
    }
    private void GetRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print(request.getSession().getAttribute(Common.atr_role));
    }
    private boolean Logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String logout = request.getParameter(Common.par_logout);
        if (logout != null && logout.equals(Common.str_true)) {
            session.removeAttribute(Common.atr_role);
            session.setAttribute(Common.atr_logged, Common.str_false);

            Cookie auth_cookie = new Cookie(Common.atr_logged, "");
            Cookie role_cookie = new Cookie(Common.atr_role, "");

            auth_cookie.setMaxAge(0);
            role_cookie.setMaxAge(0);
            response.addCookie(auth_cookie);
            response.addCookie(role_cookie);
            response.sendRedirect(Common.url_login);
            return true;
        }
        return false;
    }

    private EntityQueryHandler DefineHandler(String entity_name) throws IOException {
        switch (entity_name) {
            case "available goods":
                return new AvailableGoodsQueryHandler();
            case "customer":
                return new CustomerQueryHandler();
            case "export document":
                return new ExportDocumentQueryHandler();
            case "export goods":
                return new ExportGoodsQueryHandler();
            case "export move document":
                return new ExportMoveDocumentQueryHandler();
            case "export summary":
                return new ExportSummaryQueryHandler();
            case "goods":
                return new GoodsQueryHandler();
            case "import document":
                return new ImportDocumentQueryHandler();
            case "import goods":
                return new ImportGoodsQueryHandler();
            case "import move document":
                return new ImportMoveDocumentQueryHandler();
            case "import summary":
                return new ImportSummaryQueryHandler();
            case "provider":
                return new ProviderQueryHandler();
            case "storage":
                return new StorageQueryHandler();
            default: throw new IOException("Unknown entity = " + entity_name);
        }
    }
    private void HandleEntityQuery(BufferedReader reader, PrintWriter writer) throws IOException, JSONException {
        String entity_name = reader.readLine();
        String action = reader.readLine();

        EntityQueryHandler queryHandler = DefineHandler(entity_name);

        switch (action) {
            case Common.q_get_entity_list :
                queryHandler.GetEntityList(reader, writer);
                break;
            case Common.q_add_entity :
                queryHandler.AddEntity(reader, writer);
                break;
            case Common.q_delete_entity :
                queryHandler.DeleteEntity(reader, writer);
                break;
            case Common.q_edit_entity :
                queryHandler.EditEntity(reader, writer);
                break;
            default: throw new IOException();
        }

    }



    private void MakeImport() {

    }
    private void MakeExport() {

    }
    private void RebuildDatabase(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        DAOAbstract dao_importDocument = DAOImportDocument.getInstance();
        DAOAbstract dao_exportDocument = DAOExportDocument.getInstance();
        DAOAbstract dao_importGoods = DAOImportGoods.getInstance();
        DAOAbstract dao_exportGoods = DAOExportGoods.getInstance();
        DAOAbstract dao_availableGoods = DAOAvailableGoods.getInstance();

        Entity filter_import = dao_importGoods.createEntity();
        Entity filter_export = dao_exportGoods.createEntity();

        ArrayList<Entity> all_imported_goods = dao_importGoods.GetEntityList(connection, filter_import, false, 0,0);
        ArrayList<Entity> all_exported_goods = dao_exportGoods.GetEntityList(connection, filter_export, false, 0,0);

        HashMap<Long, Long> goods_vs_count = new HashMap<>();
        for (Entity entity : all_imported_goods) {
            ImportGoods casted_entity = (ImportGoods) entity;

            long key = casted_entity.getGoods_id();
            long value = (goods_vs_count.containsKey(key) ? goods_vs_count.get(key) : 0);
            goods_vs_count.put(key, value + casted_entity.getGoods_count());
        }
        for (Entity entity : all_exported_goods) {
            ExportGoods casted_entity = (ExportGoods) entity;

            long key = casted_entity.getGoods_id();
            long value = (goods_vs_count.containsKey(key) ? goods_vs_count.get(key) : 0);
            goods_vs_count.put(key, value - casted_entity.getGoods_count());
        }

        ArrayList<Entity> result = new ArrayList<>();
        for (Map.Entry entry : goods_vs_count.entrySet()) {
            result.add(new AvailableGoods(Entity.undefined_long, (Long) entry.getKey(), (Long) entry.getValue(), 0,0, true, new Date()));
        }

        dao_availableGoods.AddEntityList(connection, result);
        pool.DropConnection(connection);
    }
}