package main_package;

import utility_package.Common;
import utility_package.UserRole;
import database_package.DAOSystemUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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

        if (is_ok) {
            int role = dao.getUserRole(login);

            HttpSession session = request.getSession();
            session.setAttribute(Common.atr_logged, Common.str_true);

            Cookie auth_cookie = new Cookie(Common.atr_logged, Common.str_true);
            Cookie role_cookie = null;

            session.setMaxInactiveInterval(Common.session_max_age);
            auth_cookie.setMaxAge(Common.cookies_max_age);

            switch (role) {
                case 0: {   // Admin
                    session.setAttribute(Common.atr_role, UserRole.Admin.toString());
                    role_cookie = new Cookie(Common.atr_role, UserRole.Admin.toString());
                } break;
                case 1: {   // View Manager
                    session.setAttribute(Common.atr_role, UserRole.ViewManager.toString());
                    role_cookie = new Cookie(Common.atr_role, UserRole.ViewManager.toString());
                } break;
                case 2: {   // Import Manager
                    session.setAttribute(Common.atr_role, UserRole.ImportManager.toString());
                    role_cookie = new Cookie(Common.atr_role, UserRole.ImportManager.toString());
                } break;
                case 3: {   // Export Manager
                    session.setAttribute(Common.atr_role, UserRole.ExportManager.toString());
                    role_cookie = new Cookie(Common.atr_role, UserRole.ExportManager.toString());
                } break;
                default:
                    PrintWriter writer = response.getWriter();
                    writer.println("ERROR");
            }
            role_cookie.setMaxAge(Common.cookies_max_age);
            if (stay_in_system_flag) {
                response.addCookie(auth_cookie);
                response.addCookie(role_cookie);
            }

            response.sendRedirect(Common.url_menu);
        } else {
            PrintWriter writer = response.getWriter();
            writer.println("ERROR");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String logged = (String) session.getAttribute(Common.atr_logged);

        if (logged == null || logged.equals(Common.str_false)) {
            session.setAttribute(Common.atr_logged, Common.str_false);
            RequestDispatcher view = request.getRequestDispatcher(Common.jsp_login);
            view.forward(request, response);
        } else {
            PrintWriter writer = response.getWriter();
            writer.println("ERROR");
        }
    }
}
