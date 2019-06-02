package EntityServlets;

import Database.EntityQueryHandler.EntityQueryHandler;
import Database.EntityQueryHandler.ImportMoveDocumentQueryHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProviderServlet")
public class ImportMoveDocumentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = new ImportMoveDocumentQueryHandler();
        queryHandler.GetEntityList(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = new ImportMoveDocumentQueryHandler();
        queryHandler.AddEntity(request, response);
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = new ImportMoveDocumentQueryHandler();
        queryHandler.EditEntity(request, response);
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityQueryHandler queryHandler = new ImportMoveDocumentQueryHandler();
        queryHandler.DeleteEntity(request, response);
    }
}