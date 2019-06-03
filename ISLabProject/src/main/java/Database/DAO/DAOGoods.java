package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.Goods;

import java.sql.*;
import java.util.ArrayList;

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
    public ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Goods castedFilteringEntity = (Goods) filteringEntity;
        ArrayList<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.goods " +
                "WHERE (Goods_ID = ?           OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Goods_Name = ?         OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Goods_AveragePrice = ? OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Goods_Description = ?  OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setLong(index++, castedFilteringEntity.getAverage_price());
        statement.setLong(index++, castedFilteringEntity.getAverage_price());
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

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            Goods castedItem = (Goods) item;

            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.goods (Goods_Name, Goods_AveragePrice, Goods_Description) VALUES (?, ?, ?);");
            int index = 1;
            statement.setString(index++, castedItem.getName());
            statement.setLong(index++, castedItem.getAverage_price());
            statement.setString(index, castedItem.getDescription());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        Goods castedFilteringEntity = (Goods) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.goods " +
                "WHERE (Goods_ID = ?           OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Goods_Name = ?         OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Goods_AveragePrice = ? OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Goods_Description = ?  OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setString(index++, castedFilteringEntity.getName());
        statement.setLong(index++, castedFilteringEntity.getAverage_price());
        statement.setLong(index++, castedFilteringEntity.getAverage_price());
        statement.setString(index++, castedFilteringEntity.getDescription());
        statement.setString(index, castedFilteringEntity.getDescription());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.goods where Goods_ID = ?");
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        if (!set.next())
            return false;
        int count = set.getInt(1);
        if (count != 1)
            return false;

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean editEntity(Entity editingEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Goods goods = (Goods) editingEntity;

        String sqlCode =   "UPDATE islabdb.goods SET " +
                "Goods_Name = ?, " +
                "Goods_AveragePrice = ?, " +
                "Goods_Description = ? " +
                "WHERE Goods_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
        int index = 1;
        statement.setString(index++, goods.getName());
        statement.setLong(index++, goods.getAverage_price());
        statement.setString(index++, goods.getDescription());
        statement.setLong(index, goods.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sqlCode = "SELECT max(Goods_ID) FROM islabdb.goods;";
        PreparedStatement statement = connection.prepareStatement(sqlCode);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            res = resultSet.getLong(1);
        }

        pool.DropConnection(connection);
        return res;
    }

    @Override
    public Entity createEntity() {
        return new Goods();
    }
}
