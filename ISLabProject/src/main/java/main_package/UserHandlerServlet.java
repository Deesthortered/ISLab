package main_package;

import data_model.*;
import database_package.ConnectionPool;
import database_package.dao_package.*;
import database_package.entity_query_handler.*;
import org.json.JSONException;
import utility_package.Common;
import utility_package.Pair;

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
                RebuildDatabase(request, response);
                break;
            case Common.q_import:
                MakeImport(reader, writer);
                break;
            case Common.q_export:
                MakeExport(reader, writer);
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

    private void MakeImport(BufferedReader reader, PrintWriter writer) throws IOException {
        long provider_id = Long.parseLong(reader.readLine());
        int count = Integer.parseInt(reader.readLine());

        ArrayList<Long> goods_ids = new ArrayList<>();
        ArrayList<Integer> goods_counts = new ArrayList<>();
        ArrayList<Long> storage_ids = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            goods_ids.add(Long.parseLong(reader.readLine()));
            goods_counts.add(Integer.parseInt(reader.readLine()));
            storage_ids.add(Long.parseLong(reader.readLine()));
        }

        ImportDocument new_document = new ImportDocument(Entity.undefined_long, provider_id, new Date(), "");

        if (true)
            writer.print("ok");
        else
            writer.print("bad");
    }
    private void MakeExport(BufferedReader reader, PrintWriter writer) throws IOException {
        long customer_id = Long.parseLong(reader.readLine());
        int count = Integer.parseInt(reader.readLine());

        ArrayList<Long> goods_ids = new ArrayList<>();
        ArrayList<Integer> goods_counts = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            goods_ids.add(Long.parseLong(reader.readLine()));
            goods_counts.add(Integer.parseInt(reader.readLine()));
        }

        if (true)
            writer.print("ok");
        else
            writer.print("bad");
    }
    private void RebuildDatabase(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ArrayList<Entity> imported_available = new ArrayList<>();
        ArrayList<Entity> exported_available = new ArrayList<>();

        DAOAbstract dao_importDocument = DAOImportDocument.getInstance();
        Entity importDocument_filter = dao_importDocument.createEntity();
        ArrayList<Entity> all_imported_document = dao_importDocument.GetEntityList(connection, importDocument_filter, false, 0,0);

        for (Entity entity : all_imported_document) {
            ImportDocument importDocument = (ImportDocument) entity;
            DAOAbstract dao_importGoods = DAOImportGoods.getInstance();
            Entity filter_import = dao_importGoods.createEntity();
            ((ImportGoods) filter_import).setDocument_id(importDocument.getId());
            ArrayList<Entity> all_imported_goods = dao_importGoods.GetEntityList(connection, filter_import, false, 0,0);

            ArrayList<Long> storage_id = new ArrayList<>();
            for (Entity sub_entity : all_imported_goods) {
                ImportGoods importGoods = (ImportGoods) sub_entity;
                DAOAbstract dao_importMoveDocument = DAOImportMoveDocument.getInstance();
                ImportMoveDocument importMoveDocument_filter = (ImportMoveDocument) dao_importMoveDocument.createEntity();
                importMoveDocument_filter.setImportGoods_id(importGoods.getId());
                ArrayList<Entity> all_import_movedocument = dao_importMoveDocument.GetEntityList(connection, importMoveDocument_filter, false, 0,0);
                storage_id.add( ((ImportMoveDocument)all_import_movedocument.get(all_import_movedocument.size() - 1)).getStorage_id() );
            }

            for (int i = 0; i < all_imported_goods.size(); i++)
                imported_available.add(new AvailableGoods(
                        Entity.undefined_long,
                        ((ImportGoods) all_imported_goods.get(i)).getGoods_id(),
                        ((ImportGoods) all_imported_goods.get(i)).getGoods_count(),
                        importDocument.getProvider_id(),
                        storage_id.get(i),
                        false,
                        new Date()));
        }

        DAOAbstract dao_exportDocument = DAOExportDocument.getInstance();
        Entity exportDocument_filter = dao_exportDocument.createEntity();
        ArrayList<Entity> all_exported_document = dao_exportDocument.GetEntityList(connection, exportDocument_filter, false, 0,0);

        for (Entity entity : all_exported_document) {
            ExportDocument exportDocument = (ExportDocument) entity;
            DAOAbstract dao_exportGoods = DAOExportGoods.getInstance();
            ExportGoods filter_export = (ExportGoods) dao_exportGoods.createEntity();
            filter_export.setDocument_id(exportDocument.getId());
            ArrayList<Entity> all_exported_goods = dao_exportGoods.GetEntityList(connection, filter_export, false, 0, 0);

            ArrayList<Long> storage_id = new ArrayList<>();
            for (Entity sub_entity : all_exported_goods) {
                ExportGoods exportGoods = (ExportGoods) sub_entity;
                DAOAbstract dao_exportMoveDocument = DAOExportMoveDocument.getInstance();
                ExportMoveDocument exportMoveDocument_filter = (ExportMoveDocument) dao_exportMoveDocument.createEntity();
                exportMoveDocument_filter.setExportGoods_id(exportGoods.getId());
                ArrayList<Entity> all_export_movedocument = dao_exportMoveDocument.GetEntityList(connection, exportMoveDocument_filter, false, 0,0);
                storage_id.add( ((ExportMoveDocument)all_export_movedocument.get(all_export_movedocument.size() - 1)).getStorage_id() );
            }

            for (int i = 0; i < all_exported_goods.size(); i++)
                exported_available.add(new AvailableGoods(
                        Entity.undefined_long,
                        ((ExportGoods) all_exported_goods.get(i)).getGoods_id(),
                        ((ExportGoods) all_exported_goods.get(i)).getGoods_count(),
                        Entity.undefined_long,
                        storage_id.get(i),
                        false,
                        new Date()));
        }

        HashMap<Long, HashMap<Long, Pair<Long,Long>>> storage_goodsId_count_provider = new HashMap<>();
        for (Entity entity : imported_available) {
            AvailableGoods casted_entity = (AvailableGoods) entity;

            if (storage_goodsId_count_provider.containsKey(casted_entity.getStorage_id())) {
                if (storage_goodsId_count_provider.get(casted_entity.getStorage_id()).containsKey(casted_entity.getGoods_id())) {
                    Pair<Long,Long> before = storage_goodsId_count_provider.get(casted_entity.getStorage_id()).get(casted_entity.getGoods_id());
                    Pair<Long,Long> after = new Pair<>(before.getKey() + casted_entity.getGoods_count(), before.getValue());
                    storage_goodsId_count_provider.get(casted_entity.getStorage_id()).put(casted_entity.getGoods_id(), after);
                } else {
                    Pair<Long,Long> after = new Pair<>(casted_entity.getGoods_count(), casted_entity.getProvider_id());
                    storage_goodsId_count_provider.get(casted_entity.getStorage_id()).put(casted_entity.getGoods_id(), after);
                }
            } else {
                HashMap<Long, Pair<Long,Long>> sub = new HashMap<>();
                sub.put(casted_entity.getGoods_id(), new Pair<>(casted_entity.getGoods_count(), casted_entity.getProvider_id()));
                storage_goodsId_count_provider.put(casted_entity.getStorage_id(), sub);
            }
        }

        for (Entity entity : exported_available) {
            AvailableGoods casted_entity = (AvailableGoods) entity;

            if (storage_goodsId_count_provider.containsKey(casted_entity.getStorage_id())) {
                if (storage_goodsId_count_provider.get(casted_entity.getStorage_id()).containsKey(casted_entity.getGoods_id())) {
                    Pair<Long,Long> before = storage_goodsId_count_provider.get(casted_entity.getStorage_id()).get(casted_entity.getGoods_id());
                    Pair<Long,Long> after = new Pair<>(before.getKey() - casted_entity.getGoods_count(), before.getValue());
                    storage_goodsId_count_provider.get(casted_entity.getStorage_id()).put(casted_entity.getGoods_id(), after);
                } else {
                    Pair<Long,Long> after = new Pair<>(-casted_entity.getGoods_count(), casted_entity.getProvider_id());
                    storage_goodsId_count_provider.get(casted_entity.getStorage_id()).put(casted_entity.getGoods_id(), after);
                }
            } else {
                HashMap<Long, Pair<Long,Long>> sub = new HashMap<>();
                sub.put(casted_entity.getGoods_id(), new Pair<>(-casted_entity.getGoods_count(), casted_entity.getProvider_id()));
                storage_goodsId_count_provider.put(casted_entity.getStorage_id(), sub);
            }
        }

        ArrayList<Entity> result = new ArrayList<>();
        for (Long storage_key : storage_goodsId_count_provider.keySet()) {
            HashMap<Long, Pair<Long,Long>> sub_map = storage_goodsId_count_provider.get(storage_key);
            for (Long goods_key : sub_map.keySet()) {
                long goods_val = sub_map.get(goods_key).getKey();
                long provider_val = sub_map.get(goods_key).getValue();
                if (goods_val < 0) throw new IOException("Pizdets nahooy blyat!");
                else if (goods_val > 0) {
                    result.add(new AvailableGoods(
                            Entity.undefined_long,
                            goods_key,
                            goods_val,
                            provider_val,
                            storage_key,
                            true,
                            new Date()
                    ));
                }
            }
        }

        DAOAbstract dao_available = DAOAvailableGoods.getInstance();
        dao_available.DeleteEntityList(connection, new AvailableGoods());
        dao_available.AddEntityList(connection, result);
        pool.DropConnection(connection);
    }
}