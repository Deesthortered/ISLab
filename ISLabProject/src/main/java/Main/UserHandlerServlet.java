package Main;

import Utility.Common;
import Utility.UserActions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserHandlerServlet")
public class UserHandlerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserActions actions = UserActions.getInstance();
        actions.getRole(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserActions actions = UserActions.getInstance();
        if (actions.logout(request, response))
            return;
        HttpSession session = request.getSession();
        String logged = (String) session.getAttribute(Common.ATR_LOGGED);

        if (logged.equals(Common.STR_TRUE)) {
            String role = (String) session.getAttribute(Common.ATR_ROLE);
            request.setAttribute(Common.ATR_ROLE, role);
            request.getRequestDispatcher(Common.HTML_MENU).forward(request, response);
        } else {
            PrintWriter writer = response.getWriter();
            writer.println("ERROR");
        }
    }
}