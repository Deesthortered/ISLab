package Database.DAO;

import Entity.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DAOAbstract {

    ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException;
    boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException;
    boolean DeleteEntityList(Entity filter) throws ClassNotFoundException, SQLException, InterruptedException;
    boolean IsExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException;
    boolean EditEntity(Entity entity) throws SQLException, InterruptedException, ClassNotFoundException;
    long GetLastID() throws ClassNotFoundException, SQLException, InterruptedException;

    Entity createEntity();
}