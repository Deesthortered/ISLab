package EntityServlets;

import Database.EntityQueryHandler.EntityQueryHandler;
import Database.EntityQueryHandler.ImportDocumentQueryHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProviderServlet")
public class ImportDocumentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = new ImportDocumentQueryHandler();
        queryHandler.GetEntityList(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = new ImportDocumentQueryHandler();
        queryHandler.AddEntity(request, response);
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = new ImportDocumentQueryHandler();
        queryHandler.EditEntity(request, response);
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = new ImportDocumentQueryHandler();
        queryHandler.DeleteEntity(request, response);
    }
}