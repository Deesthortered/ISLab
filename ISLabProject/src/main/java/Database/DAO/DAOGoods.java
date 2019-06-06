package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.Goods;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOGoods extends DAOAbstract {

    private static DAOAbstract instance;

    private DAOGoods() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOGoods();
        }
        return instance;
    }

    private static final String SQL_GET_QUERY = "SELECT * FROM islabdb.goods " +
            "WHERE (Goods_ID = ?           OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Goods_Name = ?         OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Goods_AveragePrice = ? OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Goods_Description = ?  OR ? = \'" + Entity.UNDEFINED_STRING + "\')";

    private static final String SQL_ADD_QUERY = "INSERT INTO islabdb.goods (Goods_Name, Goods_AveragePrice, Goods_Description) VALUES (?, ?, ?);";

    private static final String SQL_DELETE_QUERY = "DELETE FROM islabdb.goods " +
            "WHERE (Goods_ID = ?           OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Goods_Name = ?         OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
            "(Goods_AveragePrice = ? OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
            "(Goods_Description = ?  OR ? = \'" + Entity.UNDEFINED_STRING + "\');";

    private static final String SQL_IS_EXIST_QUERY = "SELECT COUNT(*) from islabdb.goods where Goods_ID = ?";

    private static final String SQL_EDIT_QUERY = "UPDATE islabdb.goods SET " +
            "Goods_Name = ?, " +
            "Goods_AveragePrice = ?, " +
            "Goods_Description = ? " +
            "WHERE Goods_ID = ?;";

    private static final String SQL_GET_LAST_ID_QUERY = "SELECT max(Goods_ID) FROM islabdb.goods;";


    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Goods castedFilteringEntity = (Goods) filteringEntity;
        List<Entity> result = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL_GET_QUERY + ( limited ? " limit ? offset ?" : ""));
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setLong(index++, castedFilteringEntity.getAveragePrice());
        statement.setLong(index++, castedFilteringEntity.getAveragePrice());
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index++, castedFilteringEntity.getDescription());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("Goods_ID");
            String name = resultSet.getString("Goods_Name");
            long average_price = resultSet.getLong("Goods_AveragePrice");
            String description = resultSet.getString("Goods_Description");
            result.add(new Goods(id, name, average_price, description));
        }

        pool.dropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(List<Entity> list) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        for (Entity item : list) {
            Goods castedItem = (Goods) item;

            PreparedStatement statement = connection.prepareStatement(SQL_ADD_QUERY);
            int index = 1;
            statement.setString(index++, castedItem.getName());
            statement.setLong(index++, castedItem.getAveragePrice());
            statement.setString(index, castedItem.getDescription());
            statement.executeUpdate();
        }
        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        Goods castedFilteringEntity = (Goods) filteringEntity;
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUERY);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setLong(index++, castedFilteringEntity.getAveragePrice());
        statement.setLong(index++, castedFilteringEntity.getAveragePrice());
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index, castedFilteringEntity.getDescription());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement statement = connection.prepareStatement(SQL_IS_EXIST_QUERY);
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        if (!set.next())
            return false;
        int count = set.getInt(1);
        if (count != 1)
            return false;

        pool.dropConnection(connection);
        return true;
    }
    @Override
    public boolean editEntity(Entity editingEntity) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Goods goods = (Goods) editingEntity;

        PreparedStatement statement = connection.prepareStatement(SQL_EDIT_QUERY);
        int index = 1;
        statement.setString(index++, goods.getName());
        statement.setLong(index++, goods.getAveragePrice());
        statement.setString(index++, goods.getDescription());
        statement.setLong(index, goods.getId());
        statement.executeUpdate();

        pool.dropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        long res = -1;

        PreparedStatement statement = connection.prepareStatement(SQL_GET_LAST_ID_QUERY);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            res = resultSet.getLong(1);
        }

        pool.dropConnection(connection);
        return res;
    }

    @Override
    public Entity createEntity() {
        return new Goods();
    }
}
