package Database.DAO;

import Entity.Entity;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DAOAbstract {

    abstract public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException, ServletException;
    abstract public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException, ServletException;
    abstract public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException, ServletException;
    abstract public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException, ServletException;
    abstract public boolean editEntity(Entity editingEntity) throws SQLException, InterruptedException, ClassNotFoundException, ServletException;
    abstract public long getLastID() throws ClassNotFoundException, SQLException, InterruptedException, ServletException;

    abstract public Entity createEntity();
}