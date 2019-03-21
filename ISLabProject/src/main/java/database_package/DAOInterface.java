package database_package;

import data_model.Entity;

import java.sql.Connection;
import java.util.ArrayList;

public interface DAOInterface {

    ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records);
    boolean AddEntityList(Connection connection, ArrayList<Entity> list);
    boolean IsExistsEntity(Connection connection, long id);
    boolean DeleteEntity(Connection connection, long id);
    boolean EditEntity(Connection connection, Entity entity);
}