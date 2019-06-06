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
import java.util.List;

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

        boolean isOk;
        try {
            isOk = dao.confirmationAuthoritarian(login, password);
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }

        if (isOk) {
            request.removeAttribute(Common.ART_INVALID_CREDENTIALS);

            int role;
            try {
                role = dao.getUserRole(login);
            } catch (SQLException e) {
                throw new ServletException(e.getMessage());
            }

            HttpSession session = request.getSession();
            session.setAttribute(Common.ATR_LOGGED, Common.STR_TRUE);

            Cookie authCookie = new Cookie(Common.ATR_LOGGED, Common.STR_TRUE);
            Cookie roleCookie = null;

            session.setMaxInactiveInterval(Common.SESSION_MAX_AGE);
            authCookie.setMaxAge(Common.COOKIES_MAX_AGE);

            switch (role) {
                case 0: {   // Admin
                    session.setAttribute(Common.ATR_ROLE, UserRole.Admin.toString());
                    roleCookie = new Cookie(Common.ATR_ROLE, UserRole.Admin.toString());
                } break;
                case 1: {   // View Manager
                    session.setAttribute(Common.ATR_ROLE, UserRole.ViewManager.toString());
                    roleCookie = new Cookie(Common.ATR_ROLE, UserRole.ViewManager.toString());
                } break;
                case 2: {   // Import Manager
                    session.setAttribute(Common.ATR_ROLE, UserRole.ImportManager.toString());
                    roleCookie = new Cookie(Common.ATR_ROLE, UserRole.ImportManager.toString());
                } break;
                case 3: {   // Export Manager
                    session.setAttribute(Common.ATR_ROLE, UserRole.ExportManager.toString());
                    roleCookie = new Cookie(Common.ATR_ROLE, UserRole.ExportManager.toString());
                } break;
                default:
                    PrintWriter writer = response.getWriter();
                    writer.println("ERROR 56");
            }
            assert roleCookie != null;
            roleCookie.setMaxAge(Common.COOKIES_MAX_AGE);
            if (stayInSystemFlag) {
                response.addCookie(authCookie);
                response.addCookie(roleCookie);
            }

            response.sendRedirect(Common.urlMenu);
        } else {
            request.setAttribute(Common.ART_INVALID_CREDENTIALS, Common.STR_TRUE);
            request.getRequestDispatcher(Common.HTML_LOGIN).forward(request, response);
        }
    }

    public void getRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print(request.getSession().getAttribute(Common.ATR_ROLE));
    }
    public boolean logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String logout = request.getParameter(Common.PAR_LOGOUT);
        if (logout != null && logout.equals(Common.STR_TRUE)) {
            session.removeAttribute(Common.ATR_ROLE);
            session.setAttribute(Common.ATR_LOGGED, Common.STR_FALSE);

            Cookie authCookie = new Cookie(Common.ATR_LOGGED, "");
            Cookie roleCookie = new Cookie(Common.ATR_ROLE, "");

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
        long providerId = Long.parseLong(reader.readLine());
        int count = Integer.parseInt(reader.readLine());

        List<Long> goodsIds = new ArrayList<>();
        List<Integer> goodsCounts = new ArrayList<>();
        List<Long> storageIds = new ArrayList<>();
        List<Long> pricesIds = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            goodsIds.add(Long.parseLong(reader.readLine()));
            goodsCounts.add(Integer.parseInt(reader.readLine()));
            storageIds.add(Long.parseLong(reader.readLine()));
            pricesIds.add(Long.parseLong(reader.readLine()));
        }

        String description = reader.readLine();

        DAOAbstract daoDocument = DAOImportDocument.getInstance();
        DAOAbstract daoImportGoods = DAOImportGoods.getInstance();
        DAOAbstract daoMoveDocument = DAOImportMoveDocument.getInstance();

        Entity newDocument = new ImportDocument(Entity.UNDEFINED_LONG, providerId, new Date(), description);

        try {
            List<Entity> documentInArray = new ArrayList<>();
            documentInArray.add(newDocument);
            try {
                if (!daoDocument.addEntityList(documentInArray)) throw new ServletException("1!");
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());            }
            long documentId = 0;
            try {
                documentId = daoDocument.getLastID();
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }

            for (int i = 0; i < count; i++) {

                Entity newImportedGoods = new ImportGoods(Entity.UNDEFINED_LONG, documentId, goodsIds.get(i), goodsCounts.get(i), pricesIds.get(i));
                List<Entity> importedGoodsInArray = new ArrayList<>();
                importedGoodsInArray.add(newImportedGoods);
                try {
                    if (!daoImportGoods.addEntityList(importedGoodsInArray)) throw new ServletException("2!");
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }

                long importGoodsId = 0;
                try {
                    importGoodsId = daoImportGoods.getLastID();
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }
                Entity newMoveDocument = new ImportMoveDocument(Entity.UNDEFINED_LONG, importGoodsId, storageIds.get(i));
                List<Entity> moveDocumentInArray = new ArrayList<>();
                moveDocumentInArray.add(newMoveDocument);
                try {
                    if (!daoMoveDocument.addEntityList(moveDocumentInArray)) throw new IOException("3!");
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
        long customerId = Long.parseLong(reader.readLine());
        int count = Integer.parseInt(reader.readLine());

        List<Long> availableIds = new ArrayList<>();
        List<Integer> goodsCounts = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            availableIds.add(Long.parseLong(reader.readLine()));
            goodsCounts.add(Integer.parseInt(reader.readLine()));
        }

        String description = reader.readLine();

        DAOAbstract daoGoods = DAOGoods.getInstance();
        DAOAbstract daoDocument = DAOExportDocument.getInstance();
        DAOAbstract daoExportGoods = DAOExportGoods.getInstance();
        DAOAbstract daoMoveDocument = DAOExportMoveDocument.getInstance();
        DAOAbstract daoAvailable = DAOAvailableGoods.getInstance();

        Entity newDocument = new ExportDocument(Entity.UNDEFINED_LONG, customerId, new Date(), description);

        List<Entity> documentInArray = new ArrayList<>();
        documentInArray.add(newDocument);
        try {
            if (!daoDocument.addEntityList(documentInArray)) throw new IOException("blyat1!");
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
        long documentId = 0;
        try {
            documentId = daoDocument.getLastID();
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }

        for (int i = 0; i < count; i++) {
            Entity filter = new AvailableGoods();
            filter.setId(availableIds.get(i));
            List<Entity> currentAvailableList = null;
            try {
                currentAvailableList = daoAvailable.getEntityList(filter, false, 0, 0);
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }
            AvailableGoods res = (AvailableGoods) currentAvailableList.get(0);

            filter = new Goods();
            filter.setId(res.getGoodsId());
            List<Entity> currentGoodsList = null;
            try {
                currentGoodsList = daoGoods.getEntityList(filter, false, 0, 0);
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }
            Goods res1 = (Goods) currentGoodsList.get(0);

            Entity newExportedGoods = new ExportGoods(Entity.UNDEFINED_LONG, documentId, res.getGoodsId(), goodsCounts.get(i), res1.getAveragePrice());
            List<Entity> exportedGoodsInArray = new ArrayList<>();
            exportedGoodsInArray.add(newExportedGoods);
            try {
                if (!daoExportGoods.addEntityList(exportedGoodsInArray)) throw new IOException("blyat2!");
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }

            long exportGoodsId = 0;
            try {
                exportGoodsId = daoExportGoods.getLastID();
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }
            Entity newMoveDocument = new ExportMoveDocument(Entity.UNDEFINED_LONG, exportGoodsId, res.getStorageId());
            List<Entity> moveDocumentInArray = new ArrayList<>();
            moveDocumentInArray.add(newMoveDocument);
            try {
                if (!daoMoveDocument.addEntityList(moveDocumentInArray)) throw new IOException("blyat3!");
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }
        }
    }

    public void rebuildDatabase() throws IOException, ServletException {
        List<Entity> importedAvailable = new ArrayList<>();
        List<Entity> exportedAvailable = new ArrayList<>();

        DAOAbstract daoImportDocument = DAOImportDocument.getInstance();
        Entity importDocumentFilteringEntity = daoImportDocument.createEntity();
        List<Entity> allImportedDocument = null;
        try {
            allImportedDocument = daoImportDocument.getEntityList(importDocumentFilteringEntity, false, 0,0);
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
        for (Entity entity : allImportedDocument) {
            ImportDocument importDocument = (ImportDocument) entity;
            DAOAbstract daoImportGoods = DAOImportGoods.getInstance();
            Entity filterImport = daoImportGoods.createEntity();
            ((ImportGoods) filterImport).setDocumentId(importDocument.getId());
            List<Entity> allImportedGoods;
            try {
                allImportedGoods = daoImportGoods.getEntityList(filterImport, false, 0,0);
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }

            List<Long> storageId = new ArrayList<>();
            for (Entity subEntity : allImportedGoods) {
                ImportGoods importGoods = (ImportGoods) subEntity;
                DAOAbstract daoImportMoveDocument = DAOImportMoveDocument.getInstance();
                ImportMoveDocument importMoveDocumentFilteringEntity = (ImportMoveDocument) daoImportMoveDocument.createEntity();
                importMoveDocumentFilteringEntity.setImportGoodsId(importGoods.getId());
                List<Entity> allImportMoveDocument;
                try {
                    allImportMoveDocument = daoImportMoveDocument.getEntityList(importMoveDocumentFilteringEntity, false, 0,0);
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }
                storageId.add( ((ImportMoveDocument)allImportMoveDocument.get(allImportMoveDocument.size() - 1)).getStorageId() );
            }

            for (int i = 0; i < allImportedGoods.size(); i++)
                importedAvailable.add(new AvailableGoods(
                        Entity.UNDEFINED_LONG,
                        ((ImportGoods) allImportedGoods.get(i)).getGoodsId(),
                        ((ImportGoods) allImportedGoods.get(i)).getGoodsCount(),
                        importDocument.getProviderId(),
                        storageId.get(i),
                        false,
                        new Date()));
        }

        DAOAbstract daoExportDocument = DAOExportDocument.getInstance();
        Entity exportDocumentFilteringEntity = daoExportDocument.createEntity();
        List<Entity> allExportedDocument = null;
        try {
            allExportedDocument = daoExportDocument.getEntityList(exportDocumentFilteringEntity, false, 0,0);
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
        for (Entity entity : allExportedDocument) {
            ExportDocument exportDocument = (ExportDocument) entity;
            DAOAbstract daoExportGoods = DAOExportGoods.getInstance();
            ExportGoods filterExport = (ExportGoods) daoExportGoods.createEntity();
            filterExport.setDocumentId(exportDocument.getId());
            List<Entity> allExportedGoods = null;
            try {
                allExportedGoods = daoExportGoods.getEntityList(filterExport, false, 0, 0);
            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                throw new ServletException(e.getMessage());
            }

            List<Long> storageId = new ArrayList<>();
            for (Entity subEntity : allExportedGoods) {
                ExportGoods exportGoods = (ExportGoods) subEntity;
                DAOAbstract daoExportMoveDocument = DAOExportMoveDocument.getInstance();
                ExportMoveDocument exportMoveDocumentFilteringEntity = (ExportMoveDocument) daoExportMoveDocument.createEntity();
                exportMoveDocumentFilteringEntity.setExportGoodsId(exportGoods.getId());
                List<Entity> allExportMoveDocument = null;
                try {
                    allExportMoveDocument = daoExportMoveDocument.getEntityList(exportMoveDocumentFilteringEntity, false, 0,0);
                } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                    throw new ServletException(e.getMessage());
                }
                storageId.add( ((ExportMoveDocument)allExportMoveDocument.get(allExportMoveDocument.size() - 1)).getStorageId() );
            }

            for (int i = 0; i < allExportedGoods.size(); i++)
                exportedAvailable.add(new AvailableGoods(
                        Entity.UNDEFINED_LONG,
                        ((ExportGoods) allExportedGoods.get(i)).getGoodsId(),
                        ((ExportGoods) allExportedGoods.get(i)).getGoodsCount(),
                        Entity.UNDEFINED_LONG,
                        storageId.get(i),
                        false,
                        new Date()));
        }

        HashMap<Long, HashMap<Long, Pair<Long,Long>>> storageGoodsIdCountProvider = new HashMap<>();
        for (Entity entity : importedAvailable) {
            AvailableGoods castedEntity = (AvailableGoods) entity;

            if (storageGoodsIdCountProvider.containsKey(castedEntity.getStorageId())) {
                if (storageGoodsIdCountProvider.get(castedEntity.getStorageId()).containsKey(castedEntity.getGoodsId())) {
                    Pair<Long,Long> before = storageGoodsIdCountProvider.get(castedEntity.getStorageId()).get(castedEntity.getGoodsId());
                    Pair<Long,Long> after = new Pair<>(before.getKey() + castedEntity.getGoodsCount(), before.getValue());
                    storageGoodsIdCountProvider.get(castedEntity.getStorageId()).put(castedEntity.getGoodsId(), after);
                } else {
                    Pair<Long,Long> after = new Pair<>(castedEntity.getGoodsCount(), castedEntity.getProviderId());
                    storageGoodsIdCountProvider.get(castedEntity.getStorageId()).put(castedEntity.getGoodsId(), after);
                }
            } else {
                HashMap<Long, Pair<Long,Long>> sub = new HashMap<>();
                sub.put(castedEntity.getGoodsId(), new Pair<>(castedEntity.getGoodsCount(), castedEntity.getProviderId()));
                storageGoodsIdCountProvider.put(castedEntity.getStorageId(), sub);
            }
        }
        for (Entity entity : exportedAvailable) {
            AvailableGoods castedEntity = (AvailableGoods) entity;

            if (storageGoodsIdCountProvider.containsKey(castedEntity.getStorageId())) {
                if (storageGoodsIdCountProvider.get(castedEntity.getStorageId()).containsKey(castedEntity.getGoodsId())) {
                    Pair<Long,Long> before = storageGoodsIdCountProvider.get(castedEntity.getStorageId()).get(castedEntity.getGoodsId());
                    Pair<Long,Long> after = new Pair<>(before.getKey() - castedEntity.getGoodsCount(), before.getValue());
                    storageGoodsIdCountProvider.get(castedEntity.getStorageId()).put(castedEntity.getGoodsId(), after);
                } else {
                    Pair<Long,Long> after = new Pair<>(-castedEntity.getGoodsCount(), castedEntity.getProviderId());
                    storageGoodsIdCountProvider.get(castedEntity.getStorageId()).put(castedEntity.getGoodsId(), after);
                }
            } else {
                HashMap<Long, Pair<Long,Long>> sub = new HashMap<>();
                sub.put(castedEntity.getGoodsId(), new Pair<>(-castedEntity.getGoodsCount(), castedEntity.getProviderId()));
                storageGoodsIdCountProvider.put(castedEntity.getStorageId(), sub);
            }
        }

        List<Entity> result = new ArrayList<>();
        for (Long storageKey : storageGoodsIdCountProvider.keySet()) {
            HashMap<Long, Pair<Long,Long>> subMap = storageGoodsIdCountProvider.get(storageKey);
            for (Long goodsKey : subMap.keySet()) {
                long goodsVal = subMap.get(goodsKey).getKey();
                long providerVal = subMap.get(goodsKey).getValue();
                if (goodsVal < 0) throw new IOException("Pizdets nahooy blyat!");
                else if (goodsVal > 0) {
                    result.add(new AvailableGoods(
                            Entity.UNDEFINED_LONG,
                            goodsKey,
                            goodsVal,
                            providerVal,
                            storageKey,
                            true,
                            new Date()
                    ));
                }
            }
        }

        DAOAbstract daoAvailable = DAOAvailableGoods.getInstance();
        try {
            daoAvailable.deleteEntityList(new AvailableGoods(Entity.UNDEFINED_LONG, Entity.UNDEFINED_LONG, Entity.UNDEFINED_LONG, Entity.UNDEFINED_LONG, Entity.UNDEFINED_LONG, true, Entity.UNDEFINED_DATE));
            daoAvailable.addEntityList(result);
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new ServletException(e.getMessage());
        }
    }
}