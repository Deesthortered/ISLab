package Main;

import Database.ConnectionPool;
import Utility.Common;
import Utility.UserRole;
import Database.SystemUserAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(name = "AuthorizationServlet")
public class AuthorizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login_input");
        String password = request.getParameter("pass_input");
        boolean stay_in_system_flag = request.getParameter("save_session") != null;

        ConnectionPool pool = ConnectionPool.getInstance();
        SystemUserAccess dao = SystemUserAccess.getInstance();

        Connection auth_conn = pool.getConnection();
        boolean is_ok = dao.confirmationAuthoritarian(auth_conn, login, password);
        pool.dropConnection(auth_conn);

        if (is_ok) {
            request.removeAttribute(Common.art_invalid_credentials);

            Connection role_conn = pool.getConnection();
            int role = dao.getUserRole(role_conn, login);
            pool.dropConnection(role_conn);

            HttpSession session = request.getSession();
            session.setAttribute(Common.atr_logged, Common.strTrue);

            Cookie auth_cookie = new Cookie(Common.atr_logged, Common.strTrue);
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
                    writer.println("ERROR 56");
            }
            assert role_cookie != null;
            role_cookie.setMaxAge(Common.cookies_max_age);
            if (stay_in_system_flag) {
                response.addCookie(auth_cookie);
                response.addCookie(role_cookie);
            }

            response.sendRedirect(Common.urlMenu);
        } else {
            request.setAttribute(Common.art_invalid_credentials, Common.strTrue);
            request.getRequestDispatcher(Common.htmlLogin).forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.removeAttribute(Common.art_invalid_credentials);
        HttpSession session = request.getSession();
        String logged = (String) session.getAttribute(Common.atr_logged);

        if (logged == null || logged.equals(Common.strFalse)) {
            session.setAttribute(Common.atr_logged, Common.strFalse);
            RequestDispatcher view = request.getRequestDispatcher(Common.htmlLogin);
            view.forward(request, response);
        } else {
            PrintWriter writer = response.getWriter();
            writer.println("ERROR 83");
        }
    }
}