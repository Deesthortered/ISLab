package Main;

import Utility.Common;
import Utility.UserActions;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AuthorizationServlet")
public class AuthorizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserActions actions = UserActions.getInstance();
        actions.authorize(request, response);
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
