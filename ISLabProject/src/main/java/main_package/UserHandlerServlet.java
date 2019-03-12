package main_package;

import data_model.Provider;
import database_package.ConnectionPool;
import database_package.DAOProviders;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utility_package.Common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name = "UserHandlerServlet")
public class UserHandlerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();

        String title = reader.readLine();
        if (title.equals(Common.q_get_role)) {
            GetRole(request, response);
        } else if (title.equals(Common.q_get_provider_list)) {
            GetProviderList(request, response);
        } else {
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
            request.getRequestDispatcher(Common.jsp_menu).forward(request, response);
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

        int begin_index = Integer.parseInt(reader.readLine());
        int count_of_records = Integer.parseInt(reader.readLine());

        ConnectionPool pool = ConnectionPool.getInstance();
        DAOProviders dao = DAOProviders.getInstance();

        Connection connection = pool.GetConnection();
        ArrayList<Provider> list = dao.GetProvidersLIst(connection, begin_index, count_of_records);
        pool.DropConnection(connection);

        JSONArray json_list = new JSONArray();
        for (Provider provider : list) {
            JSONObject one = new JSONObject();
            try {
                one.put("id", provider.getId());
                one.put("name", provider.getName());
                one.put("country", provider.getCountry());
                one.put("description", provider.getDescription() == null ? "" : provider.getDescription());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            json_list.put(one);
        }

        writer.write(json_list.toString());
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