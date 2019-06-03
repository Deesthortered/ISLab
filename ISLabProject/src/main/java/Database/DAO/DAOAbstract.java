package Database.DAO;

import Entity.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DAOAbstract {

    ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException;
    boolean addEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException;
    boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException;
    boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException;
    boolean editEntity(Entity editingEntity) throws SQLException, InterruptedException, ClassNotFoundException;
    long getLastID() throws ClassNotFoundException, SQLException, InterruptedException;

    Entity createEntity();
}