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
    public ArrayList<Entity> GetEntityList(Entity filter, boolean limited, int start_index, int count_of_records) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Goods casted_filter = (Goods) filter;
        ArrayList<Entity> result = new ArrayList<>();

        String sql_query = "SELECT * FROM islabdb.goods " +
                "WHERE (Goods_ID = ?           OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Goods_Name = ?         OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Goods_AveragePrice = ? OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Goods_Description = ?  OR ? = \'" + Entity.undefined_string + "\')" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getName());
        statement.setLong(index++, casted_filter.getAverage_price());
        statement.setLong(index++, casted_filter.getAverage_price());
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index++, casted_filter.getDescription());
        if (limited) {
            statement.setLong(index++, count_of_records);
            statement.setLong(index++, start_index);
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
    public boolean AddEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            Goods casted_item = (Goods) item;

            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.goods (Goods_Name, Goods_AveragePrice, Goods_Description) VALUES (?, ?, ?);");
            int index = 1;
            statement.setString(index++, casted_item.getName());
            statement.setLong(index++, casted_item.getAverage_price());
            statement.setString(index, casted_item.getDescription());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean DeleteEntityList(Entity filter) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        Goods casted_filter = (Goods) filter;
        String sql_query = "DELETE FROM islabdb.goods " +
                "WHERE (Goods_ID = ?           OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Goods_Name = ?         OR ? = \'" + Entity.undefined_string + "\') AND " +
                "(Goods_AveragePrice = ? OR ? = "   + Entity.undefined_long   +   ") AND " +
                "(Goods_Description = ?  OR ? = \'" + Entity.undefined_string + "\');";
        PreparedStatement statement = connection.prepareStatement(sql_query);
        int index = 1;
        statement.setLong(index++, casted_filter.getId());
        statement.setLong(index++, casted_filter.getId());
        statement.setString(index++, casted_filter.getName());
        statement.setString(index++, casted_filter.getName());
        statement.setLong(index++, casted_filter.getAverage_price());
        statement.setLong(index++, casted_filter.getAverage_price());
        statement.setString(index++, casted_filter.getDescription());
        statement.setString(index, casted_filter.getDescription());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean IsExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
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
    public boolean EditEntity(Entity entity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        Goods goods = (Goods) entity;

        String sql_code =   "UPDATE islabdb.goods SET " +
                "Goods_Name = ?, " +
                "Goods_AveragePrice = ?, " +
                "Goods_Description = ? " +
                "WHERE Goods_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sql_code);
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
    public long GetLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(Goods_ID) FROM islabdb.goods;";
        PreparedStatement statement = connection.prepareStatement(sql_code);
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
