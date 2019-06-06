package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.Goods;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOGoods implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOGoods() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOGoods();
        }
        return instance;
    }

    @Override
    public List<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Goods castedFilteringEntity = (Goods) filteringEntity;
        List<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.goods " +
                "WHERE (Goods_ID = ?           OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
                "(Goods_Name = ?         OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
                "(Goods_AveragePrice = ? OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
                "(Goods_Description = ?  OR ? = \'" + Entity.UNDEFINED_STRING + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
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
            statement.setLong(index++, startIndex);
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

            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.goods (Goods_Name, Goods_AveragePrice, Goods_Description) VALUES (?, ?, ?);");
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
        String sqlQuery = "DELETE FROM islabdb.goods " +
                "WHERE (Goods_ID = ?           OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
                "(Goods_Name = ?         OR ? = \'" + Entity.UNDEFINED_STRING + "\') AND " +
                "(Goods_AveragePrice = ? OR ? = "   + Entity.UNDEFINED_LONG +   ") AND " +
                "(Goods_Description = ?  OR ? = \'" + Entity.UNDEFINED_STRING + "\');";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
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

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.goods where Goods_ID = ?");
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

        String sqlCode =   "UPDATE islabdb.goods SET " +
                "Goods_Name = ?, " +
                "Goods_AveragePrice = ?, " +
                "Goods_Description = ? " +
                "WHERE Goods_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
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

        String sqlCode = "SELECT max(Goods_ID) FROM islabdb.goods;";
        PreparedStatement statement = connection.prepareStatement(sqlCode);
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
