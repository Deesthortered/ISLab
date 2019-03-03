package main_package;

import utility_package.Common;
import utility_package.UserRole;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserHandlerServlet")
public class UserHandlerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Logout(request, response)) {
            // response.sendRedirect(Common.url_login); // Все равно не работает :D
            return;
        }

        System.out.println("OK");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String logged = (String) session.getAttribute(Common.atr_logged);

        if (logged.equals(Common.str_true)) {

            String role = (String) session.getAttribute(Common.atr_role);
            RequestDispatcher view = null;

            if (role.equals(UserRole.Admin.toString())) {
                view = request.getRequestDispatcher(Common.jsp_menu_admin);
            } else if (role.equals(UserRole.ViewManager.toString())) {
                view = request.getRequestDispatcher(Common.jsp_menu_view_manager);
            } else if (role.equals(UserRole.ImportManager.toString())) {
                view = request.getRequestDispatcher(Common.jsp_menu_import_manager);
            } else if (role.equals(UserRole.ExportManager.toString())) {
                view = request.getRequestDispatcher(Common.jsp_menu_export_manager);
            }

            view.forward(request, response);
        } else {
            PrintWriter writer = response.getWriter();
            writer.println("ERROR");
        }
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
            return true;
        }
        return false;
    }
}