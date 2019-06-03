package Main;

import Entity.*;
import Database.ConnectionPool;
import Database.DAO.*;
import Utility.Common;
import Utility.Pair;

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
            case Common.q_import:
                MakeImport(reader, writer);
                break;
            case Common.q_export:
                MakeExport(reader, writer);
                break;
            case Common.q_rebuild_reports:
                RebuildDatabase();
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

        if (logged.equals(Common.strTrue)) {
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
        if (logout != null && logout.equals(Common.strTrue)) {
            session.removeAttribute(Common.atr_role);
            session.setAttribute(Common.atr_logged, Common.strFalse);

            Cookie auth_cookie = new Cookie(Common.atr_logged, "");
            Cookie role_cookie = new Cookie(Common.atr_role, "");

            auth_cookie.setMaxAge(0);
            role_cookie.setMaxAge(0);
            response.addCookie(auth_cookie);
            response.addCookie(role_cookie);
            response.sendRedirect(Common.urlLogin);
            return true;
        }
        return false;
    }

    private void MakeImport(BufferedReader reader, PrintWriter writer) throws IOException {
        long provider_id = Long.parseLong(reader.readLine());
        int count = Integer.parseInt(reader.readLine());

        ArrayList<Long> goods_ids = new ArrayList<>();
        ArrayList<Integer> goods_counts = new ArrayList<>();
        ArrayList<Long> storage_ids = new ArrayList<>();
        ArrayList<Long> prices_ids = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            goods_ids.add(Long.parseLong(reader.readLine()));
            goods_counts.add(Integer.parseInt(reader.readLine()));
            storage_ids.add(Long.parseLong(reader.readLine()));
            prices_ids.add(Long.parseLong(reader.readLine()));
        }

        String description = reader.readLine();

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        DAOAbstract dao_document = DAOImportDocument.getInstance();
        DAOAbstract dao_importgoods = DAOImportGoods.getInstance();
        DAOAbstract dao_movedocument = DAOImportMoveDocument.getInstance();

        Entity new_document = new ImportDocument(Entity.undefined_long, provider_id, new Date(), description);

        try {
            ArrayList<Entity> document_in_array = new ArrayList<>();
            document_in_array.add(new_document);
            if (!dao_document.addEntityList(connection, document_in_array)) throw new IOException("blyat1!");
            long document_id = dao_document.getLastID(connection);

            for (int i = 0; i < count; i++) {

                Entity new_imported_goods = new ImportGoods(Entity.undefined_long, document_id, goods_ids.get(i), goods_counts.get(i), prices_ids.get(i));
                ArrayList<Entity> imported_goods_in_array = new ArrayList<>();
                imported_goods_in_array.add(new_imported_goods);
                if (!dao_importgoods.addEntityList(connection, imported_goods_in_array)) throw new IOException("blyat2!");

                long import_goods_id = dao_importgoods.getLastID(connection);
                Entity new_move_document = new ImportMoveDocument(Entity.undefined_long, import_goods_id, storage_ids.get(i));
                ArrayList<Entity> move_document_in_array = new ArrayList<>();
                move_document_in_array.add(new_move_document);
                if (!dao_movedocument.addEntityList(connection, move_document_in_array)) throw new IOException("blyat3!");
            }
            RebuildDatabase(); //!!!
            writer.print("ok");
        } catch (IOException e) {
            writer.print("bad");
        } finally {
            pool.dropConnection(connection);
        }
    }
    private void MakeExport(BufferedReader reader, PrintWriter writer) throws IOException {
        long customer_id = Long.parseLong(reader.readLine());
        int count = Integer.parseInt(reader.readLine());

        ArrayList<Long> available_ids = new ArrayList<>();
        ArrayList<Integer> goods_counts = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            available_ids.add(Long.parseLong(reader.readLine()));
            goods_counts.add(Integer.parseInt(reader.readLine()));
        }

        String description = reader.readLine();

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        DAOAbstract dao_goods = DAOGoods.getInstance();
        DAOAbstract dao_document = DAOExportDocument.getInstance();
        DAOAbstract dao_exportgoods = DAOExportGoods.getInstance();
        DAOAbstract dao_movedocument = DAOExportMoveDocument.getInstance();
        DAOAbstract dao_available = DAOAvailableGoods.getInstance();

        Entity new_document = new ExportDocument(Entity.undefined_long, customer_id, new Date(), description);

        try {
            ArrayList<Entity> document_in_array = new ArrayList<>();
            document_in_array.add(new_document);
            if (!dao_document.addEntityList(connection, document_in_array)) throw new IOException("blyat1!");
            long document_id = dao_document.getLastID(connection);

            for (int i = 0; i < count; i++) {
                Entity filter = new AvailableGoods();
                filter.setId(available_ids.get(i));
                ArrayList<Entity> current_available_list = dao_available.getEntityList(connection, filter, false, 0, 0);
                AvailableGoods res = (AvailableGoods) current_available_list.get(0);

                filter = new Goods();
                filter.setId(res.getGoods_id());
                ArrayList<Entity> current_goods_list = dao_goods.getEntityList(connection, filter, false, 0, 0);
                Goods res1 = (Goods) current_goods_list.get(0);

                Entity new_exported_goods = new ExportGoods(Entity.undefined_long, document_id, res.getGoods_id(), goods_counts.get(i), res1.getAverage_price());
                ArrayList<Entity> exported_goods_in_array = new ArrayList<>();
                exported_goods_in_array.add(new_exported_goods);
                if (!dao_exportgoods.addEntityList(connection, exported_goods_in_array)) throw new IOException("blyat2!");

                long export_goods_id = dao_exportgoods.getLastID(connection);
                Entity new_move_document = new ExportMoveDocument(Entity.undefined_long, export_goods_id, res.getStorage_id());
                ArrayList<Entity> move_document_in_array = new ArrayList<>();
                move_document_in_array.add(new_move_document);
                if (!dao_movedocument.addEntityList(connection, move_document_in_array)) throw new IOException("blyat3!");
            }

            RebuildDatabase(); //!!!
            writer.print("ok");
        } catch (IOException e) {
            writer.print("bad");
        } finally {
            pool.dropConnection(connection);
        }
    }

    private void RebuildDatabase() throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        ArrayList<Entity> imported_available = new ArrayList<>();
        ArrayList<Entity> exported_available = new ArrayList<>();

        DAOAbstract dao_importDocument = DAOImportDocument.getInstance();
        Entity importDocument_filter = dao_importDocument.createEntity();
        ArrayList<Entity> all_imported_document = dao_importDocument.getEntityList(connection, importDocument_filter, false, 0,0);
        for (Entity entity : all_imported_document) {
            ImportDocument importDocument = (ImportDocument) entity;
            DAOAbstract dao_importGoods = DAOImportGoods.getInstance();
            Entity filter_import = dao_importGoods.createEntity();
            ((ImportGoods) filter_import).setDocument_id(importDocument.getId());
            ArrayList<Entity> all_imported_goods = dao_importGoods.getEntityList(connection, filter_import, false, 0,0);

            ArrayList<Long> storage_id = new ArrayList<>();
            for (Entity sub_entity : all_imported_goods) {
                ImportGoods importGoods = (ImportGoods) sub_entity;
                DAOAbstract dao_importMoveDocument = DAOImportMoveDocument.getInstance();
                ImportMoveDocument importMoveDocument_filter = (ImportMoveDocument) dao_importMoveDocument.createEntity();
                importMoveDocument_filter.setImportGoods_id(importGoods.getId());
                ArrayList<Entity> all_import_movedocument = dao_importMoveDocument.getEntityList(connection, importMoveDocument_filter, false, 0,0);
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
        ArrayList<Entity> all_exported_document = dao_exportDocument.getEntityList(connection, exportDocument_filter, false, 0,0);
        for (Entity entity : all_exported_document) {
            ExportDocument exportDocument = (ExportDocument) entity;
            DAOAbstract dao_exportGoods = DAOExportGoods.getInstance();
            ExportGoods filter_export = (ExportGoods) dao_exportGoods.createEntity();
            filter_export.setDocument_id(exportDocument.getId());
            ArrayList<Entity> all_exported_goods = dao_exportGoods.getEntityList(connection, filter_export, false, 0, 0);

            ArrayList<Long> storage_id = new ArrayList<>();
            for (Entity sub_entity : all_exported_goods) {
                ExportGoods exportGoods = (ExportGoods) sub_entity;
                DAOAbstract dao_exportMoveDocument = DAOExportMoveDocument.getInstance();
                ExportMoveDocument exportMoveDocument_filter = (ExportMoveDocument) dao_exportMoveDocument.createEntity();
                exportMoveDocument_filter.setExportGoods_id(exportGoods.getId());
                ArrayList<Entity> all_export_movedocument = dao_exportMoveDocument.getEntityList(connection, exportMoveDocument_filter, false, 0,0);
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
        dao_available.deleteEntityList(connection, new AvailableGoods(Entity.undefined_long, Entity.undefined_long, Entity.undefined_long, Entity.undefined_long, Entity.undefined_long, true, Entity.undefined_date));
        dao_available.addEntityList(connection, result);
        pool.dropConnection(connection);
    }
}