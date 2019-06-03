package Utility;

import Database.DAO.*;
import Database.SystemUserAccess;
import Entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserActions {
    private static UserActions instance;

    private UserActions() {

    }
    public static synchronized UserActions getInstance() {
        if (instance == null) {
            instance = new UserActions();
        }
        return instance;
    }

    public void authorize(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login_input");
        String password = request.getParameter("pass_input");
        boolean stayInSystemFlag = request.getParameter("save_session") != null;

        SystemUserAccess dao = SystemUserAccess.getInstance();

        boolean is_ok;
        try {
            is_ok = dao.confirmationAuthoritarian(login, password);
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }

        if (is_ok) {
            request.removeAttribute(Common.art_invalid_credentials);

            int role;
            try {
                role = dao.getUserRole(login);
            } catch (SQLException e) {
                throw new ServletException(e.getMessage());
            }

            HttpSession session = request.getSession();
            session.setAttribute(Common.atr_logged, Common.strTrue);

            Cookie authCookie = new Cookie(Common.atr_logged, Common.strTrue);
            Cookie roleCookie = null;

            session.setMaxInactiveInterval(Common.session_max_age);
            authCookie.setMaxAge(Common.cookies_max_age);

            switch (role) {
                case 0: {   // Admin
                    session.setAttribute(Common.atr_role, UserRole.Admin.toString());
                    roleCookie = new Cookie(Common.atr_role, UserRole.Admin.toString());
                } break;
                case 1: {   // View Manager
                    session.setAttribute(Common.atr_role, UserRole.ViewManager.toString());
                    roleCookie = new Cookie(Common.atr_role, UserRole.ViewManager.toString());
                } break;
                case 2: {   // Import Manager
                    session.setAttribute(Common.atr_role, UserRole.ImportManager.toString());
                    roleCookie = new Cookie(Common.atr_role, UserRole.ImportManager.toString());
                } break;
                case 3: {   // Export Manager
                    session.setAttribute(Common.atr_role, UserRole.ExportManager.toString());
                    roleCookie = new Cookie(Common.atr_role, UserRole.ExportManager.toString());
                } break;
                default:
                    PrintWriter writer = response.getWriter();
                    writer.println("ERROR 56");
            }
            assert roleCookie != null;
            roleCookie.setMaxAge(Common.cookies_max_age);
            if (stayInSystemFlag) {
                response.addCookie(authCookie);
                response.addCookie(roleCookie);
            }

            response.sendRedirect(Common.urlMenu);
        } else {
            request.setAttribute(Common.art_invalid_credentials, Common.strTrue);
            request.getRequestDispatcher(Common.htmlLogin).forward(request, response);
        }
    }

    public void getRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print(request.getSession().getAttribute(Common.atr_role));
    }
    public boolean logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String logout = request.getParameter(Common.par_logout);
        if (logout != null && logout.equals(Common.strTrue)) {
            session.removeAttribute(Common.atr_role);
            session.setAttribute(Common.atr_logged, Common.strFalse);

            Cookie authCookie = new Cookie(Common.atr_logged, "");
            Cookie roleCookie = new Cookie(Common.atr_role, "");

            authCookie.setMaxAge(0);
            roleCookie.setMaxAge(0);
            response.addCookie(authCookie);
            response.addCookie(roleCookie);
            response.sendRedirect(Common.urlLogin);
            return true;
        }
        return false;
    }

    public void makeImport(BufferedReader reader, PrintWriter writer) throws IOException {
        long provider_id = Long.parseLong(reader.readLine());
        int count = Integer.parseInt(reader.readLine());

        ArrayList<Long> goodsIds = new ArrayList<>();
        ArrayList<Integer> goodsCounts = new ArrayList<>();
        ArrayList<Long> storageIds = new ArrayList<>();
        ArrayList<Long> pricesIds = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            goodsIds.add(Long.parseLong(reader.readLine()));
            goodsCounts.add(Integer.parseInt(reader.readLine()));
            storageIds.add(Long.parseLong(reader.readLine()));
            pricesIds.add(Long.parseLong(reader.readLine()));
        }

        String description = reader.readLine();

        DAOAbstract dao_document = DAOImportDocument.getInstance();
        DAOAbstract dao_importgoods = DAOImportGoods.getInstance();
        DAOAbstract dao_movedocument = DAOImportMoveDocument.getInstance();

        Entity new_document = new ImportDocument(Entity.undefined_long, provider_id, new Date(), description);

        try {
            ArrayList<Entity> document_in_array = new ArrayList<>();
            document_in_array.add(new_document);
            try {
                if (!dao_document.addEntityList(document_in_array)) throw new ServletException("1!");
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());            }
            long document_id = 0;
            try {
                document_id = dao_document.getLastID();
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }

            for (int i = 0; i < count; i++) {

                Entity new_imported_goods = new ImportGoods(Entity.undefined_long, document_id, goodsIds.get(i), goodsCounts.get(i), pricesIds.get(i));
                ArrayList<Entity> imported_goods_in_array = new ArrayList<>();
                imported_goods_in_array.add(new_imported_goods);
                try {
                    if (!dao_importgoods.addEntityList(imported_goods_in_array)) throw new ServletException("2!");
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }

                long import_goods_id = 0;
                try {
                    import_goods_id = dao_importgoods.getLastID();
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }
                Entity new_move_document = new ImportMoveDocument(Entity.undefined_long, import_goods_id, storageIds.get(i));
                ArrayList<Entity> move_document_in_array = new ArrayList<>();
                move_document_in_array.add(new_move_document);
                try {
                    if (!dao_movedocument.addEntityList(move_document_in_array)) throw new IOException("3!");
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }
            }
            rebuildDatabase();
            writer.print("ok");
        } catch (IOException | ServletException e) {
            writer.print("bad");
        }
    }
    public void makeExport(BufferedReader reader, PrintWriter writer) throws IOException, ServletException {
        long customer_id = Long.parseLong(reader.readLine());
        int count = Integer.parseInt(reader.readLine());

        ArrayList<Long> available_ids = new ArrayList<>();
        ArrayList<Integer> goods_counts = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            available_ids.add(Long.parseLong(reader.readLine()));
            goods_counts.add(Integer.parseInt(reader.readLine()));
        }

        String description = reader.readLine();

        DAOAbstract dao_goods = DAOGoods.getInstance();
        DAOAbstract dao_document = DAOExportDocument.getInstance();
        DAOAbstract dao_exportgoods = DAOExportGoods.getInstance();
        DAOAbstract dao_movedocument = DAOExportMoveDocument.getInstance();
        DAOAbstract dao_available = DAOAvailableGoods.getInstance();

        Entity new_document = new ExportDocument(Entity.undefined_long, customer_id, new Date(), description);

        ArrayList<Entity> document_in_array = new ArrayList<>();
        document_in_array.add(new_document);
        try {
            if (!dao_document.addEntityList(document_in_array)) throw new IOException("blyat1!");
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
        long document_id = 0;
        try {
            document_id = dao_document.getLastID();
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }

        for (int i = 0; i < count; i++) {
            Entity filter = new AvailableGoods();
            filter.setId(available_ids.get(i));
            ArrayList<Entity> current_available_list = null;
            try {
                current_available_list = dao_available.getEntityList(filter, false, 0, 0);
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }
            AvailableGoods res = (AvailableGoods) current_available_list.get(0);

            filter = new Goods();
            filter.setId(res.getGoods_id());
            ArrayList<Entity> current_goods_list = null;
            try {
                current_goods_list = dao_goods.getEntityList(filter, false, 0, 0);
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }
            Goods res1 = (Goods) current_goods_list.get(0);

            Entity new_exported_goods = new ExportGoods(Entity.undefined_long, document_id, res.getGoods_id(), goods_counts.get(i), res1.getAverage_price());
            ArrayList<Entity> exported_goods_in_array = new ArrayList<>();
            exported_goods_in_array.add(new_exported_goods);
            try {
                if (!dao_exportgoods.addEntityList(exported_goods_in_array)) throw new IOException("blyat2!");
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }

            long export_goods_id = 0;
            try {
                export_goods_id = dao_exportgoods.getLastID();
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }
            Entity new_move_document = new ExportMoveDocument(Entity.undefined_long, export_goods_id, res.getStorage_id());
            ArrayList<Entity> move_document_in_array = new ArrayList<>();
            move_document_in_array.add(new_move_document);
            try {
                if (!dao_movedocument.addEntityList(move_document_in_array)) throw new IOException("blyat3!");
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }
        }
    }

    public void rebuildDatabase() throws IOException, ServletException {
        ArrayList<Entity> imported_available = new ArrayList<>();
        ArrayList<Entity> exported_available = new ArrayList<>();

        DAOAbstract dao_importDocument = DAOImportDocument.getInstance();
        Entity importDocument_filter = dao_importDocument.createEntity();
        ArrayList<Entity> all_imported_document = null;
        try {
            all_imported_document = dao_importDocument.getEntityList(importDocument_filter, false, 0,0);
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
        for (Entity entity : all_imported_document) {
            ImportDocument importDocument = (ImportDocument) entity;
            DAOAbstract dao_importGoods = DAOImportGoods.getInstance();
            Entity filter_import = dao_importGoods.createEntity();
            ((ImportGoods) filter_import).setDocument_id(importDocument.getId());
            ArrayList<Entity> all_imported_goods;
            try {
                all_imported_goods = dao_importGoods.getEntityList(filter_import, false, 0,0);
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }

            ArrayList<Long> storage_id = new ArrayList<>();
            for (Entity sub_entity : all_imported_goods) {
                ImportGoods importGoods = (ImportGoods) sub_entity;
                DAOAbstract dao_importMoveDocument = DAOImportMoveDocument.getInstance();
                ImportMoveDocument importMoveDocument_filter = (ImportMoveDocument) dao_importMoveDocument.createEntity();
                importMoveDocument_filter.setImportGoods_id(importGoods.getId());
                ArrayList<Entity> all_import_movedocument;
                try {
                    all_import_movedocument = dao_importMoveDocument.getEntityList(importMoveDocument_filter, false, 0,0);
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }
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
        ArrayList<Entity> all_exported_document = null;
        try {
            all_exported_document = dao_exportDocument.getEntityList(exportDocument_filter, false, 0,0);
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
        for (Entity entity : all_exported_document) {
            ExportDocument exportDocument = (ExportDocument) entity;
            DAOAbstract dao_exportGoods = DAOExportGoods.getInstance();
            ExportGoods filter_export = (ExportGoods) dao_exportGoods.createEntity();
            filter_export.setDocument_id(exportDocument.getId());
            ArrayList<Entity> all_exported_goods = null;
            try {
                all_exported_goods = dao_exportGoods.getEntityList(filter_export, false, 0, 0);
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }

            ArrayList<Long> storage_id = new ArrayList<>();
            for (Entity sub_entity : all_exported_goods) {
                ExportGoods exportGoods = (ExportGoods) sub_entity;
                DAOAbstract dao_exportMoveDocument = DAOExportMoveDocument.getInstance();
                ExportMoveDocument exportMoveDocument_filter = (ExportMoveDocument) dao_exportMoveDocument.createEntity();
                exportMoveDocument_filter.setExportGoods_id(exportGoods.getId());
                ArrayList<Entity> all_export_movedocument = null;
                try {
                    all_export_movedocument = dao_exportMoveDocument.getEntityList(exportMoveDocument_filter, false, 0,0);
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }
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
        try {
            dao_available.deleteEntityList(new AvailableGoods(Entity.undefined_long, Entity.undefined_long, Entity.undefined_long, Entity.undefined_long, Entity.undefined_long, true, Entity.undefined_date));
            dao_available.addEntityList(result);
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
    }
}