package main_package;

import database_package.DAOSystemUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AuthorizationServlet")
public class AuthorizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login_input");
        String password = request.getParameter("pass_input");
        boolean stay_in_system_flag = request.getParameter("save_session") != null;

        DAOSystemUser dao = DAOSystemUser.getInstance();
        boolean is_ok = dao.ConfirmateAuthorizaion(login, password);

        PrintWriter writer = response.getWriter();
        writer.println(is_ok ? "ok" : "bad");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/login_page.html");
        view.forward(request, response);
    }
}
