package Database.DAO;

import Entity.Entity;

import java.sql.Connection;
import java.util.ArrayList;

public interface DAOAbstract {

    ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records);
    boolean AddEntityList(Connection connection, ArrayList<Entity> list);
    boolean DeleteEntityList(Connection connection, Entity filter);
    boolean IsExistsEntity(Connection connection, long id);
    boolean EditEntity(Connection connection, Entity entity);
    long GetLastID(Connection connection);

    Entity createEntity();
}