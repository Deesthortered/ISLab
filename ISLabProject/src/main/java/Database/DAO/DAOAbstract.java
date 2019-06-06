package Database.DAO;

import Entity.Entity;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DAOAbstract {

    List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException, ServletException;
    boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException, ServletException;
    boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException, ServletException;
    boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException, ServletException;
    boolean editEntity(Entity editingEntity) throws SQLException, InterruptedException, ClassNotFoundException, ServletException;
    long getLastID() throws ClassNotFoundException, SQLException, InterruptedException, ServletException;

    Entity createEntity();
}