package main_package;

import data_model.*;
import database_package.ConnectionPool;
import database_package.dao_package.*;
import database_package.entity_query_handler.*;
import org.json.JSONArray;
import org.json.JSONException;
import utility_package.Common;
import utility_package.DateHandler;
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
            case Common.q_import:
                MakeImport(reader, writer);
                break;
            case Common.q_export:
                MakeExport(reader, writer);
                break;
            case Common.q_rebuild_reports:
                RebuildDatabase();
                break;
            case Common.q_get_report_available:
                MakeReportAvailableQuery(reader, writer);
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
        ArrayList<Long> prices_ids = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            goods_ids.add(Long.parseLong(reader.readLine()));
            goods_counts.add(Integer.parseInt(reader.readLine()));
            storage_ids.add(Long.parseLong(reader.readLine()));
            prices_ids.add(Long.parseLong(reader.readLine()));
        }

        String description = reader.readLine();

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        DAOAbstract dao_document = DAOImportDocument.getInstance();
        DAOAbstract dao_importgoods = DAOImportGoods.getInstance();
        DAOAbstract dao_movedocument = DAOImportMoveDocument.getInstance();

        Entity new_document = new ImportDocument(Entity.undefined_long, provider_id, new Date(), description);

        try {
            ArrayList<Entity> document_in_array = new ArrayList<>();
            document_in_array.add(new_document);
            if (!dao_document.AddEntityList(connection, document_in_array)) throw new IOException("blyat1!");
            long document_id = dao_document.GetLastID(connection);

            for (int i = 0; i < count; i++) {

                Entity new_imported_goods = new ImportGoods(Entity.undefined_long, document_id, goods_ids.get(i), goods_counts.get(i), prices_ids.get(i));
                ArrayList<Entity> imported_goods_in_array = new ArrayList<>();
                imported_goods_in_array.add(new_imported_goods);
                if (!dao_importgoods.AddEntityList(connection, imported_goods_in_array)) throw new IOException("blyat2!");

                long import_goods_id = dao_importgoods.GetLastID(connection);
                Entity new_move_document = new ImportMoveDocument(Entity.undefined_long, import_goods_id, storage_ids.get(i));
                ArrayList<Entity> move_document_in_array = new ArrayList<>();
                move_document_in_array.add(new_move_document);
                if (!dao_movedocument.AddEntityList(connection, move_document_in_array)) throw new IOException("blyat3!");
            }
            RebuildDatabase(); //!!!
            writer.print("ok");
        } catch (IOException e) {
            writer.print("bad");
        } finally {
            pool.DropConnection(connection);
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
        Connection connection = pool.GetConnection();

        DAOAbstract dao_goods = DAOGoods.getInstance();
        DAOAbstract dao_document = DAOExportDocument.getInstance();
        DAOAbstract dao_exportgoods = DAOExportGoods.getInstance();
        DAOAbstract dao_movedocument = DAOExportMoveDocument.getInstance();
        DAOAbstract dao_available = DAOAvailableGoods.getInstance();

        Entity new_document = new ExportDocument(Entity.undefined_long, customer_id, new Date(), description);

        try {
            ArrayList<Entity> document_in_array = new ArrayList<>();
            document_in_array.add(new_document);
            if (!dao_document.AddEntityList(connection, document_in_array)) throw new IOException("blyat1!");
            long document_id = dao_document.GetLastID(connection);

            for (int i = 0; i < count; i++) {
                Entity filter = new AvailableGoods();
                filter.setId(available_ids.get(i));
                ArrayList<Entity> current_available_list = dao_available.GetEntityList(connection, filter, false, 0, 0);
                AvailableGoods res = (AvailableGoods) current_available_list.get(0);

                filter = new Goods();
                filter.setId(res.getGoods_id());
                ArrayList<Entity> current_goods_list = dao_goods.GetEntityList(connection, filter, false, 0, 0);
                Goods res1 = (Goods) current_goods_list.get(0);

                Entity new_exported_goods = new ExportGoods(Entity.undefined_long, document_id, res.getGoods_id(), goods_counts.get(i), res1.getAverage_price());
                ArrayList<Entity> exported_goods_in_array = new ArrayList<>();
                exported_goods_in_array.add(new_exported_goods);
                if (!dao_exportgoods.AddEntityList(connection, exported_goods_in_array)) throw new IOException("blyat2!");

                long export_goods_id = dao_exportgoods.GetLastID(connection);
                Entity new_move_document = new ExportMoveDocument(Entity.undefined_long, export_goods_id, res.getStorage_id());
                ArrayList<Entity> move_document_in_array = new ArrayList<>();
                move_document_in_array.add(new_move_document);
                if (!dao_movedocument.AddEntityList(connection, move_document_in_array)) throw new IOException("blyat3!");
            }

            RebuildDatabase(); //!!!
            writer.print("ok");
        } catch (IOException e) {
            writer.print("bad");
        } finally {
            pool.DropConnection(connection);
        }
    }


    private void RebuildDatabase() throws IOException {
        RebuildReports();
        RebuildAvailable();
    }
    private void RebuildReports() throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ArrayList<Entity> ImportSummaries = new ArrayList<>();

        DAOImportDocument daoImportDoc = (DAOImportDocument) DAOImportDocument.getInstance();
        ImportDocument importFirst = daoImportDoc.GetEarlierDocument(connection);
        ImportDocument importLast = daoImportDoc.GetLatestDocument(connection);

        Date current_date = importFirst.getImport_date();
        Date last_date = importLast.getImport_date();
        while (current_date.before(last_date)) {
            Pair<Date, Date> current_boundaries = DateHandler.getMonthBoundaries(current_date);
            ImportSummaries.add(makeImportSummary(connection, current_boundaries.getKey(), current_boundaries.getValue()));
            current_date = current_boundaries.getValue();
        }

        ArrayList<Entity> ExportSummaries = new ArrayList<>();

        DAOExportDocument daoExportDoc = (DAOExportDocument) DAOExportDocument.getInstance();
        ExportDocument exportFirst = daoExportDoc.GetEarlierDocument(connection);
        ExportDocument exportLast = daoExportDoc.GetLatestDocument(connection);

        current_date = exportFirst.getExport_date();
        last_date = exportLast.getExport_date();
        while (current_date.before(last_date)) {
            Pair<Date, Date> current_boundaries = DateHandler.getMonthBoundaries(current_date);
            ExportSummaries.add(makeExportSummary(connection, current_boundaries.getKey(), current_boundaries.getValue()));
            current_date = current_boundaries.getValue();
        }

        DAOAbstract daoAvailableGoods = DAOAvailableGoods.getInstance();
        Entity filter = new AvailableGoods();
        daoAvailableGoods.DeleteEntityList(connection, filter);

        if (importFirst.getImport_date().before(exportFirst.getExport_date()))
            current_date = importFirst.getImport_date();
        else current_date = exportFirst.getExport_date();
        if (importLast.getImport_date().before(exportLast.getExport_date()))
            last_date = exportLast.getExport_date();
        else  last_date = importLast.getImport_date();

        rebuildSummaryAvailableGoods(connection, current_date, last_date);

        DAOAbstract daoImportSummary = DAOImportSummary.getInstance();
        DAOAbstract daoExportSummary = DAOExportSummary.getInstance();

        Entity empty_ImportSummary = new ImportSummary();
        Entity empty_ExportSummary = new ExportSummary();
        daoImportSummary.DeleteEntityList(connection, empty_ImportSummary);
        daoExportSummary.DeleteEntityList(connection, empty_ExportSummary);

        daoImportSummary.AddEntityList(connection, ImportSummaries);
        daoExportSummary.AddEntityList(connection, ExportSummaries);

        pool.DropConnection(connection);
    }
    private void RebuildAvailable() throws IOException {
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
        dao_available.DeleteEntityList(connection, new AvailableGoods(Entity.undefined_long, Entity.undefined_long, Entity.undefined_long, Entity.undefined_long, Entity.undefined_long, true, Entity.undefined_date));
        dao_available.AddEntityList(connection, result);
        pool.DropConnection(connection);
    }

    private void MakeReportAvailableQuery(BufferedReader reader, PrintWriter writer) throws IOException {
        String entity_name = reader.readLine();
        long id = Long.parseLong(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        Date date = null;
        if (entity_name.equals(Common.q_import_report)) {
            DAOAbstract daoImportSummary = DAOImportSummary.getInstance();
            Entity filter = new ImportSummary();
            filter.setId(id);
            ArrayList<Entity> imports = daoImportSummary.GetEntityList(connection, filter, false, 0, 0);
            date = ((ImportSummary) imports.get(0)).getEnd_date();
        } else if (entity_name.equals(Common.q_export_report)) {
            DAOAbstract daoExportSummary = DAOExportSummary.getInstance();
            Entity filter = new ExportSummary();
            filter.setId(id);
            ArrayList<Entity> imports = daoExportSummary.GetEntityList(connection, filter, false, 0, 0);
            date = ((ExportSummary) imports.get(0)).getEnd_date();
        } else {
            writer.println("bad");
            return;
        }

        ArrayList<Entity> result = (ArrayList<Entity>)(ArrayList<?>) MakeAvailableGoodsForReport(date);

        JSONArray json_list = new JSONArray();
        for (Entity entity : result) {
            ArrayList<String> represantiveData = new ArrayList<>();
            ArrayList<Long> foreingKeys = entity.getForeingKeys();
            ArrayList<DAOAbstract> dao_array = entity.getForeingDAO();
            if (dao_array != null)
                for (int i = 0; i < dao_array.size(); i++) {
                    Entity sub_filter = dao_array.get(i).createEntity();
                    sub_filter.setId(foreingKeys.get(i));
                    Entity sub_entity = dao_array.get(i).GetEntityList(connection, sub_filter, true, 0, 1).get(0);
                    represantiveData.add(sub_entity.getRepresantiveData());
                }
            json_list.put(entity.getJSON(represantiveData));
        }
        pool.DropConnection(connection);
        writer.write(json_list.toString());
    }

    private Pair<ImportSummary, ExportSummary> MakeReportSummary(Date from, Date to) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        DAOImportSummary daoImportSummary = (DAOImportSummary) DAOImportSummary.getInstance();
        DAOExportSummary daoExportSummary = (DAOExportSummary) DAOExportSummary.getInstance();

        ArrayList<ImportSummary> importSummaries = daoImportSummary.GetSummaryBetweenDates(connection, from, to);
        ArrayList<ExportSummary> exportSummaries = daoExportSummary.GetSummaryBetweenDates(connection, from, to);

        if (from.before(importSummaries.get(0).getStart_date())) {
            importSummaries.add(0, makeImportSummary(connection, from, importSummaries.get(0).getStart_date()));
            exportSummaries.add(0, makeExportSummary(connection, from, importSummaries.get(0).getStart_date()));
        }

        if (to.after(importSummaries.get(importSummaries.size() - 1).getEnd_date())){
            importSummaries.add(makeImportSummary(connection, to, importSummaries.get(importSummaries.size() - 1).getEnd_date()));
            exportSummaries.add(makeExportSummary(connection, to, importSummaries.get(importSummaries.size() - 1).getEnd_date()));
        }

        assert (importSummaries.size() == exportSummaries.size());

        int  countImport = 0;
        int  countExport = 0;
        long amountImport = 0;
        long amountExport = 0;
        long max_priceImport = 0;
        long max_priceExport = 0;
        long min_priceImport = 0;
        long min_priceExport = 0;
        boolean f = true;
        for (int i = 0; i < importSummaries.size(); i++) {
            ImportSummary currentImportSummary = importSummaries.get(i);
            ExportSummary currentExportSummary = exportSummaries.get(i);

            countImport += currentImportSummary.getImports_count();
            countExport += currentExportSummary.getExports_count();

            amountImport += currentImportSummary.getImports_amount();
            amountExport += currentExportSummary.getExports_amount();

            max_priceImport = Math.max(max_priceImport, currentImportSummary.getMax_price());
            max_priceExport = Math.max(max_priceExport, currentExportSummary.getMax_price());

            if (f) {
                min_priceImport = currentImportSummary.getMin_price();
                min_priceExport = currentExportSummary.getMin_price();
                f = false;
            } else {
                min_priceImport = Math.max(min_priceImport, currentImportSummary.getMin_price());
                min_priceExport = Math.max(min_priceExport, currentExportSummary.getMin_price());
            }
        }
        pool.DropConnection(connection);

        ImportSummary resultImport = new ImportSummary(Entity.undefined_long, from, to, countImport, amountImport, max_priceImport, min_priceImport);
        ExportSummary resultExport = new ExportSummary(Entity.undefined_long, from, to, countExport, amountExport, max_priceExport, min_priceExport);

        return new Pair<>(resultImport, resultExport);
    }
    private ArrayList<AvailableGoods> MakeAvailableGoodsForReport(Date to) throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        Date beforeTo = (Date) to.clone();
        to = DateHandler.nextDay(to);

        DAOImportDocument daoImportDocument = (DAOImportDocument) DAOImportDocument.getInstance();
        Date from = daoImportDocument.GetEarlierDocument(connection).getImport_date();

        DAOAvailableGoods daoAvailableGoods = (DAOAvailableGoods) DAOAvailableGoods.getInstance();
        ArrayList<AvailableGoods> availableList = daoAvailableGoods.GetAvailableGoodsBetweenDates(connection, beforeTo, to);

        if (availableList.isEmpty()) {
            ArrayList<AvailableGoods> tmp = makeAvailableGoodsForReports(connection, new ArrayList<>(), new ArrayList<>(), from, to);
            availableList.addAll(tmp);
        } else {
            Date last_there = DateHandler.nextDay(availableList.get(availableList.size()-1).getSnapshot_date());
            if (to.after(last_there)) {
                ArrayList<Entity> import_available = (ArrayList<Entity>) availableList.clone();
                ArrayList<AvailableGoods> tmp = makeAvailableGoodsForReports(connection, import_available, new ArrayList<>(), availableList.get(availableList.size()-1).getSnapshot_date(), to);
                availableList.addAll(tmp);
            }
        }

        pool.DropConnection(connection);
        return availableList;
    }
    private ImportSummary makeImportSummary(Connection connection, Date from, Date to) {
        DAOImportDocument daoImportDoc = (DAOImportDocument) DAOImportDocument.getInstance();
        ArrayList<ImportDocument> list = daoImportDoc.GetDocumentsBetweenDates(connection, from, to);

        int count = list.size();
        long amount = 0;
        long max_price = 0;
        long min_price = 0;
        boolean f = true;
        DAOAbstract daoImportGoods = DAOImportGoods.getInstance();
        for (ImportDocument item : list) {
            Entity goodsFilter = new ImportGoods(Entity.undefined_long, item.getId(), Entity.undefined_long, Entity.undefined_long, Entity.undefined_long);
            ArrayList<Entity> goodsList = daoImportGoods.GetEntityList(connection, goodsFilter, false, 0, 0);

            for (Entity item2 : goodsList) {
                ImportGoods goods = (ImportGoods) item2;

                amount += goods.getGoods_price();
                max_price = Math.max(max_price, goods.getGoods_price());
                if (f) { min_price = goods.getGoods_price(); f = false; }
                else min_price = Math.min(max_price, goods.getGoods_price());
            }
        }
        return new ImportSummary(Entity.undefined_long, from, to, count, amount, max_price, min_price);
    }
    private ExportSummary makeExportSummary(Connection connection, Date from, Date to) {
        DAOExportDocument daoExportDoc = (DAOExportDocument) DAOExportDocument.getInstance();
        ArrayList<ExportDocument> list = daoExportDoc.GetDocumentsBetweenDates(connection, from, to);

        int count = list.size();
        long amount = 0;
        long max_price = 0;
        long min_price = 0;
        boolean f = true;
        DAOAbstract daoExportGoods = DAOExportGoods.getInstance();
        for (ExportDocument item : list) {
            Entity goodsFilter = new ExportGoods(Entity.undefined_long, item.getId(), Entity.undefined_long, Entity.undefined_long, Entity.undefined_long);
            ArrayList<Entity> goodsList = daoExportGoods.GetEntityList(connection, goodsFilter, false, 0, 0);

            for (Entity item2 : goodsList) {
                ExportGoods goods = (ExportGoods) item2;

                amount += goods.getGoods_price();
                max_price = Math.max(max_price, goods.getGoods_price());
                if (f) { min_price = goods.getGoods_price(); f = false; }
                else min_price = Math.min(max_price, goods.getGoods_price());
            }
        }
        return new ExportSummary(Entity.undefined_long, from, to, count, amount, max_price, min_price);
    }

    private void rebuildSummaryAvailableGoods(Connection connection, Date from, Date last) throws IOException {
        DAOAbstract daoAvailableGoods = DAOAvailableGoods.getInstance();

        ArrayList<Entity> imported_available = new ArrayList<>();
        ArrayList<Entity> exported_available = new ArrayList<>();

        Date current = (Date) from.clone();
        while (current.before(last)) {
            Pair<Date, Date> current_boundaries = DateHandler.getMonthBoundaries(current);
            ArrayList<Entity> tmp = (ArrayList<Entity>)(ArrayList<?>) makeAvailableGoodsForReports(connection, imported_available, exported_available, current_boundaries.getKey(), current_boundaries.getValue());
            daoAvailableGoods.AddEntityList(connection, tmp);
            current = current_boundaries.getValue();
        }
    }
    private ArrayList<AvailableGoods> makeAvailableGoodsForReports(Connection connection, ArrayList<Entity> imported_available, ArrayList<Entity> exported_available, Date from, Date to) throws IOException {
        DAOImportDocument dao_importDocument = (DAOImportDocument) DAOImportDocument.getInstance();
        ArrayList<ImportDocument> all_imported_document = dao_importDocument.GetDocumentsBetweenDates(connection, from, to);
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

        DAOExportDocument dao_exportDocument = (DAOExportDocument) DAOExportDocument.getInstance();
        ArrayList<ExportDocument> all_exported_document = dao_exportDocument.GetDocumentsBetweenDates(connection, from, to);
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

        ArrayList<AvailableGoods> available_goods = new ArrayList<>();
        for (Long storage_key : storage_goodsId_count_provider.keySet()) {
            HashMap<Long, Pair<Long,Long>> sub_map = storage_goodsId_count_provider.get(storage_key);
            for (Long goods_key : sub_map.keySet()) {
                long goods_val = sub_map.get(goods_key).getKey();
                long provider_val = sub_map.get(goods_key).getValue();
                if (goods_val < 0) throw new IOException("Pizdets nahooy blyat!");
                else if (goods_val > 0) {
                    available_goods.add(new AvailableGoods(
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

        for (AvailableGoods item : available_goods) {
            item.setCurrent(false);
            item.setSnapshot_date(to);
        }
        return available_goods;
    }
}