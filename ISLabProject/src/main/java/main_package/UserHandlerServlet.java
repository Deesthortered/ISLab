package main_package;

import data_model.*;
import database_package.*;
import org.json.JSONArray;
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

@WebServlet(name = "UserHandlerServlet")
public class UserHandlerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();

        String title = reader.readLine();
        if (title.equals(Common.q_get_role)) {
            GetRole(request, response);
        }

        else if (title.equals(Common.q_get_provider_list)) {
            GetProviderList(request, response);
        } else if (title.equals(Common.q_add_provider)) {
            AddNewProvider(request, response);
        } else if (title.equals(Common.q_delete_provider)) {
            DeleteProvider(request, response);
        } else if (title.equals(Common.q_edit_provider)) {
            EditProvider(request, response);
        }

        else if (title.equals(Common.q_get_customer_list)) {
            GetCustomerList(request, response);
        } else if (title.equals(Common.q_add_customer)) {
            AddNewCustomer(request, response);
        } else if (title.equals(Common.q_delete_customer)) {
            DeleteCustomer(request, response);
        } else if (title.equals(Common.q_edit_customer)) {
            EditCustomer(request, response);
        }

        else if (title.equals(Common.q_get_goods_list)) {
            GetGoodsList(request, response);
        } else if (title.equals(Common.q_add_goods)) {
            AddNewGoods(request, response);
        } else if (title.equals(Common.q_delete_goods)) {
            DeleteGoods(request, response);
        } else if (title.equals(Common.q_edit_goods)) {
            EditGoods(request, response);
        }

        else if (title.equals(Common.q_get_import_document_list)) {
            GetImportDocumentList(request, response);
        } else if (title.equals(Common.q_get_export_document_list)) {
            GetExportDocumentList(request, response);
        } else if (title.equals(Common.q_add_import_document)) {
            AddNewImportDocument(request, response);
        } else if (title.equals(Common.q_add_export_document)) {
            AddNewExportDocument(request, response);
        }

        else if (title.equals(Common.q_rebuild_base)) {
            RebuildAvailableGoodsBase(request, response);
        }

        else {
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

    private void GetRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print(request.getSession().getAttribute(Common.atr_role));
    }

    private void GetProviderList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        String tmp = reader.readLine();
        long id = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        String name = reader.readLine();
        String country = reader.readLine();
        String description = reader.readLine();

        Entity filter = new Provider(id, name, country, description);

        boolean limited = Boolean.parseBoolean(reader.readLine());
        int begin_index = Integer.parseInt(reader.readLine());
        int count_of_records = Integer.parseInt(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOProviders.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = dao.GetEntityList(connection, filter, limited, begin_index, count_of_records);
        pool.DropConnection(connection);

        JSONArray json_list = new JSONArray();
        for (Entity provider : list) {
            json_list.put(provider.getJSON());
        }

        writer.write(json_list.toString());
    }
    private void AddNewProvider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        reader.readLine(); // To miss unnecessary id field

        String name = reader.readLine();
        String country = reader.readLine();
        String description = reader.readLine();

        Provider provider = new Provider(-1, name, country, description);

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOProviders.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = new ArrayList<>();
        list.add(provider);
        if (dao.AddEntityList(connection, list))
            writer.print("ok");
        else
            writer.print("bad");
        pool.DropConnection(connection);

    }
    private void DeleteProvider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        long id = Long.parseLong(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOProviders.getInstance();

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
    private void EditProvider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        long id = Long.parseLong(reader.readLine());

        String name = reader.readLine();
        String country = reader.readLine();
        String description = reader.readLine();

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOProviders.getInstance();

        Connection connection = pool.GetConnection();
        Provider old_version = (Provider) dao.GetEntityList(connection, new Provider(id, null, null, null), true, 0, 1).get(0);

        if (name.isEmpty()) name = old_version.getName();
        if (country.isEmpty()) country = old_version.getCountry();
        if (description.isEmpty()) description = old_version.getDescription();

        if (dao.IsExistsEntity(connection, id)) {
            if (dao.EditEntity(connection, new Provider(id, name, country, description)))
                writer.print("ok");
            else
                writer.print("bad");
        } else
            writer.print("not exist");
        pool.DropConnection(connection);
    }

    private void GetCustomerList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        String tmp = reader.readLine();
        long id = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        String name = reader.readLine();
        String country = reader.readLine();
        String description = reader.readLine();

        Customer filter = new Customer(id, name, country, description);

        boolean limited = Boolean.parseBoolean(reader.readLine());
        int begin_index = Integer.parseInt(reader.readLine());
        int count_of_records = Integer.parseInt(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOCustomer.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = dao.GetEntityList(connection, filter, limited, begin_index, count_of_records);
        pool.DropConnection(connection);

        JSONArray json_list = new JSONArray();
        for (Entity customer : list) {
            json_list.put(customer.getJSON());
        }

        writer.write(json_list.toString());
    }
    private void AddNewCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        reader.readLine(); // To miss unnecessary id field

        String name = reader.readLine();
        String country = reader.readLine();
        String description = reader.readLine();

        Customer customer = new Customer(-1, name, country, description);

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOCustomer.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = new ArrayList<>();
        list.add(customer);
        if (dao.AddEntityList(connection, list))
            writer.print("ok");
        else
            writer.print("bad");
        pool.DropConnection(connection);

    }
    private void DeleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        long id = Long.parseLong(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOCustomer.getInstance();

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
    private void EditCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        long id = Long.parseLong(reader.readLine());

        String name = reader.readLine();
        String country = reader.readLine();
        String description = reader.readLine();

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOCustomer.getInstance();

        Connection connection = pool.GetConnection();
        Customer old_version = (Customer) dao.GetEntityList(connection, new Customer(id, null, null, null), true, 0, 1).get(0);

        if (name.isEmpty()) name = old_version.getName();
        if (country.isEmpty()) country = old_version.getCountry();
        if (description.isEmpty()) description = old_version.getDescription();

        if (dao.IsExistsEntity(connection, id)) {
            if (dao.EditEntity(connection, new Customer(id, name, country, description)))
                writer.print("ok");
            else
                writer.print("bad");
        } else
            writer.print("not exist");
        pool.DropConnection(connection);
    }

    private void GetGoodsList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        String tmp = reader.readLine();
        long id = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        String name = reader.readLine();

        tmp = reader.readLine();
        long average = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        String description = reader.readLine();

        Goods filter = new Goods(id, name, average, description);

        boolean limited = Boolean.parseBoolean(reader.readLine());
        int begin_index = Integer.parseInt(reader.readLine());
        int count_of_records = Integer.parseInt(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOGoods.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = dao.GetEntityList(connection, filter, limited, begin_index, count_of_records);
        pool.DropConnection(connection);

        JSONArray json_list = new JSONArray();
        for (Entity goods : list) {
            json_list.put(goods.getJSON());
        }

        writer.write(json_list.toString());
    }
    private void AddNewGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        reader.readLine(); // To miss unnecessary id field

        String name = reader.readLine();
        long average = Long.parseLong(reader.readLine());
        String description = reader.readLine();

        Goods goods = new Goods(-1, name, average, description);

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOGoods.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = new ArrayList<>();
        list.add(goods);
        if (dao.AddEntityList(connection, list))
            writer.print("ok");
        else
            writer.print("bad");
        pool.DropConnection(connection);
    }
    private void DeleteGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        long id = Long.parseLong(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOGoods.getInstance();

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
    private void EditGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        long id = Long.parseLong(reader.readLine());

        String name = reader.readLine();
        long average = Long.parseLong(reader.readLine());
        String description = reader.readLine();

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOGoods.getInstance();

        Connection connection = pool.GetConnection();
        Goods old_version = (Goods) dao.GetEntityList(connection, new Goods(id, null, -1, null), true, 0, 1).get(0);

        if (name.isEmpty()) name = old_version.getName();
        if (average == -1) average = old_version.getAverage_price();
        if (description.isEmpty()) description = old_version.getDescription();

        if (dao.IsExistsEntity(connection, id)) {
            if (dao.EditEntity(connection, new Goods(id, name, average, description)))
                writer.print("ok");
            else
                writer.print("bad");
        } else
            writer.print("not exist");
        pool.DropConnection(connection);
    }

    private void GetImportDocumentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        String tmp = reader.readLine();
        long id = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        tmp = reader.readLine();
        long provider_id = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        tmp = reader.readLine();
        Date import_date = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_date : Common.SQLDateToJavaDate(tmp) );

        String description = reader.readLine();

        ImportDocument filter = new ImportDocument(id, provider_id, import_date, description);

        boolean limited = Boolean.parseBoolean(reader.readLine());
        int begin_index = Integer.parseInt(reader.readLine());
        int count_of_records = Integer.parseInt(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOImportDocument.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = dao.GetEntityList(connection, filter, limited, begin_index, count_of_records);
        pool.DropConnection(connection);

        JSONArray json_list = new JSONArray();
        connection = pool.GetConnection();
        DAOInterface sub_dao = DAOProviders.getInstance();
        for (Entity entity : list) {
            ImportDocument importDocument = (ImportDocument) entity;
            Provider provider = new Provider(importDocument.getProvider_id(), Entity.undefined_string, Entity.undefined_string, Entity.undefined_string);
            json_list.put(importDocument.getParametrizedJSON(((Provider) sub_dao.GetEntityList(connection, provider, true, 0 ,1).get(0)).getName()));
        }
        pool.DropConnection(connection);
        writer.write(json_list.toString());
    }
    private void AddNewImportDocument(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        reader.readLine(); // To miss unnecessary id field

        String tmp = reader.readLine();
        long provider_id = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        tmp = reader.readLine();
        Date import_date = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_date : Common.SQLDateToJavaDate(tmp) );

        String description = reader.readLine();

        ImportDocument document = new ImportDocument(-1, provider_id, import_date, description);

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOImportDocument.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = new ArrayList<>();
        list.add(document);
        if (dao.AddEntityList(connection, list))
            writer.print("ok");
        else
            writer.print("bad");
        pool.DropConnection(connection);
    }


    private void GetExportDocumentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        String tmp = reader.readLine();
        long id = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        tmp = reader.readLine();
        long customer_id = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        tmp = reader.readLine();
        Date export_date = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_date : Common.SQLDateToJavaDate(tmp) );

        String description = reader.readLine();

        ExportDocument filter = new ExportDocument(id, customer_id, export_date, description);

        boolean limited = Boolean.parseBoolean(reader.readLine());
        int begin_index = Integer.parseInt(reader.readLine());
        int count_of_records = Integer.parseInt(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOExportDocument.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = dao.GetEntityList(connection, filter, limited, begin_index, count_of_records);
        pool.DropConnection(connection);

        JSONArray json_list = new JSONArray();
        connection = pool.GetConnection();
        DAOInterface sub_dao = DAOCustomer.getInstance();
        for (Entity entity : list) {
            ExportDocument exportDocument = (ExportDocument) entity;
            Customer customer = new Customer(exportDocument.getCustomer_id(), Entity.undefined_string, Entity.undefined_string, Entity.undefined_string);
            json_list.put(exportDocument.getParametrizedJSON(((Customer) sub_dao.GetEntityList(connection, customer, true, 0 ,1).get(0)).getName()));
        }
        pool.DropConnection(connection);
        writer.write(json_list.toString());
    }
    private void AddNewExportDocument(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        reader.readLine(); // To miss unnecessary id field

        String tmp = reader.readLine();
        long customer_id = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_long : Long.parseLong(tmp) );

        tmp = reader.readLine();
        Date export_date = ( tmp.equals(Entity.undefined_string) ? Entity.undefined_date : Common.SQLDateToJavaDate(tmp) );

        String description = reader.readLine();

        ExportDocument document = new ExportDocument(-1, customer_id, export_date, description);

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOInterface dao = DAOExportDocument.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Entity> list = new ArrayList<>();
        list.add(document);
        if (dao.AddEntityList(connection, list))
            writer.print("ok");
        else
            writer.print("bad");
        pool.DropConnection(connection);
    }

    private void RebuildAvailableGoodsBase(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();



    }

    private void SendError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();

        reader.reset();
        String error = "ERROR: Client query title = " + reader.readLine();
        writer.println(error);
        System.out.println(error);
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
}