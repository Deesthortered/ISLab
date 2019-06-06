package EntityServlets;

import Database.EntityQueryHandler.EntityQueryHandler;
import Database.EntityQueryHandler.ImportDocumentQueryHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ImportDocumentServlet")
public class ImportDocumentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = ImportDocumentQueryHandler.getInstance();
        if (queryHandler != null) {
            queryHandler.getEntityList(request, response);
        } else throw new ServletException();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = ImportDocumentQueryHandler.getInstance();
        if (queryHandler != null) {
            queryHandler.addEntity(request, response);
        } else throw new ServletException();
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = ImportDocumentQueryHandler.getInstance();
        if (queryHandler != null) {
            queryHandler.editEntity(request, response);
        } else throw new ServletException();
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = ImportDocumentQueryHandler.getInstance();
        if (queryHandler != null) {
            queryHandler.deleteEntity(request, response);
        } else throw new ServletException();
    }
}