package Database.DAO;

import Database.ConnectionPool;
import Entity.Entity;
import Entity.ImportGoods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOImportGoods implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOImportGoods() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOImportGoods();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> getEntityList(Entity filteringEntity, boolean limited, int startIndex, int countOfRecords) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        ImportGoods castedFilteringEntity = (ImportGoods) filteringEntity;
        ArrayList<Entity> result = new ArrayList<>();

        String sqlQuery = "SELECT * FROM islabdb.importgoods " +
                "WHERE (ImportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ")" +
                ( limited ? " limit ? offset ?" : "");

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getDocument_id());
        statement.setLong(index++, castedFilteringEntity.getDocument_id());
        statement.setLong(index++, castedFilteringEntity.getGoods_id());
        statement.setLong(index++, castedFilteringEntity.getGoods_id());
        statement.setLong(index++, castedFilteringEntity.getGoods_count());
        statement.setLong(index++, castedFilteringEntity.getGoods_count());
        statement.setLong(index++, castedFilteringEntity.getGoods_price());
        statement.setLong(index++, castedFilteringEntity.getGoods_price());
        if (limited) {
            statement.setLong(index++, countOfRecords);
            statement.setLong(index, startIndex);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("ImportGoods_ID");
            long documentId = resultSet.getLong("ImportGoods_DocumentID");
            long goodsId = resultSet.getLong("ImportGoods_GoodsID");
            long goodsCount = resultSet.getLong("ImportGoods_GoodsCount");
            long goodsPrice = resultSet.getLong("ImportGoods_GoodsPrice");
            result.add(new ImportGoods(id, documentId, goodsId, goodsCount, goodsPrice));
        }

        pool.DropConnection(connection);
        return result;
    }
    @Override
    public boolean addEntityList(ArrayList<Entity> list) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        for (Entity item : list) {
            ImportGoods castedItem = (ImportGoods) item;

            PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importgoods (ImportGoods_DocumentID, ImportGoods_GoodsID, ImportGoods_GoodsCount, ImportGoods_GoodsPrice) VALUES (?, ?, ?, ?);");
            int index = 1;
            statement.setLong(index++, castedItem.getDocument_id());
            statement.setLong(index++, castedItem.getGoods_id());
            statement.setLong(index++, castedItem.getGoods_count());
            statement.setLong(index, castedItem.getGoods_price());
            statement.executeUpdate();
        }
        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean deleteEntityList(Entity filteringEntity) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        ImportGoods castedFilteringEntity = (ImportGoods) filteringEntity;
        String sqlQuery = "DELETE FROM islabdb.importgoods " +
                "WHERE (ImportGoods_ID = ?         OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_DocumentID = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsID = ?    OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsCount = ? OR ? = " + Entity.undefined_long + ") AND " +
                "(ImportGoods_GoodsPrice = ? OR ? = " + Entity.undefined_long + ");";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        int index = 1;
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getId());
        statement.setLong(index++, castedFilteringEntity.getDocument_id());
        statement.setLong(index++, castedFilteringEntity.getDocument_id());
        statement.setLong(index++, castedFilteringEntity.getGoods_id());
        statement.setLong(index++, castedFilteringEntity.getGoods_id());
        statement.setLong(index++, castedFilteringEntity.getGoods_count());
        statement.setLong(index++, castedFilteringEntity.getGoods_count());
        statement.setLong(index++, castedFilteringEntity.getGoods_price());
        statement.setLong(index, castedFilteringEntity.getGoods_price());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }
    @Override
    public boolean isExistsEntity(long id) throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importgoods where ImportGoods_ID = ?");
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
        ImportGoods importGoods = (ImportGoods) editingEntity;

        String sqlCode =   "UPDATE islabdb.importgoods SET ImportGoods_DocumentID = ?, " +
                "ImportGoods_GoodsID = ?, " +
                "ImportGoods_GoodsCount = ?, " +
                "ImportGoods_GoodsPrice = ? " +
                "WHERE ImportGoods_ID = ?;";

        PreparedStatement statement = connection.prepareStatement(sqlCode);
        int index = 1;
        statement.setLong(index++, importGoods.getDocument_id());
        statement.setLong(index++, importGoods.getGoods_id());
        statement.setLong(index++, importGoods.getGoods_count());
        statement.setLong(index++, importGoods.getGoods_price());
        statement.setLong(index, importGoods.getId());
        statement.executeUpdate();

        pool.DropConnection(connection);
        return true;
    }

    @Override
    public long getLastID() throws ClassNotFoundException, SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.GetConnection();
        long res = -1;

        String sql_code = "SELECT max(ImportGoods_ID) FROM islabdb.importgoods;";
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
        return new ImportGoods();
    }
}
