package Main;

import Utility.UserActions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ExportServlet")
public class ExportServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserActions actions = UserActions.getInstance();
        actions.makeExport(request.getReader(), response.getWriter());
    }
}