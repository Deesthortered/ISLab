package database_package.dao_package;

import data_model.Entity;
import data_model.ImportSummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static utility_package.Common.JavaDateToSQLDate;

public class DAOImportSummary implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOImportSummary() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOImportSummary();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        ImportSummary casted_filter = (ImportSummary) filter;
        ArrayList<Entity> result = new ArrayList<>();

        try {
            String sql_query = "SELECT * FROM islabdb.importsummary " +
                      "WHERE (Summary_ID = ?            OR ? = "   + Entity.undefined_long + ") AND " +
                            "(Summary_StartDate = ?     OR ? = \'" + JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                            "(Summary_EndDate = ?       OR ? = \'" + JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                            "(Summary_ImportsCount = ?  OR ? = "   + Entity.undefined_int  + ") AND " +
                            "(Summary_ImportsAmount = ? OR ? = "   + Entity.undefined_long + ") AND " +
                            "(Summary_MaxPrice = ?      OR ? = "   + Entity.undefined_long + ") AND " +
                            "(Summary_MinPrice = ?      OR ? = "   + Entity.undefined_long + ")" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setString(3, JavaDateToSQLDate(casted_filter.getStart_date()));
            statement.setString(4, JavaDateToSQLDate(casted_filter.getStart_date()));
            statement.setString(5, JavaDateToSQLDate(casted_filter.getEnd_date()));
            statement.setString(6, JavaDateToSQLDate(casted_filter.getEnd_date()));
            statement.setInt(7, casted_filter.getImports_count());
            statement.setInt(8, casted_filter.getImports_count());
            statement.setLong(9, casted_filter.getImports_amount());
            statement.setLong(10, casted_filter.getImports_amount());
            statement.setLong(11, casted_filter.getMax_price());
            statement.setLong(12, casted_filter.getMax_price());
            statement.setLong(13, casted_filter.getMin_price());
            statement.setLong(14, casted_filter.getMin_price());
            if (limited) {
                statement.setLong(15, count_of_records);
                statement.setLong(16, start_index);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("Summary_ID");
                Date start_date = resultSet.getDate("Summary_StartDate");
                Date end_date = resultSet.getDate("Summary_EndDate");
                int count = resultSet.getInt("Summary_ImportsCount");
                long amount = resultSet.getLong("Summary_IMportsAmount");
                long max_price = resultSet.getLong("Summary_MaxPrice");
                long min_price = resultSet.getLong("Summary_MinPrice");
                result.add(new ImportSummary(id, start_date, end_date, count, amount, max_price, min_price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            ImportSummary casted_item = (ImportSummary) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.importsummary (Summary_StartDate, Summary_EndDate, Summary_ImportsCount, Summary_ImportsAmount, Summary_MaxPrice, Summary_MinPrice) VALUES (?, ?, ?, ?, ?, ?);");
                statement.setString(1, JavaDateToSQLDate(casted_item.getStart_date()));
                statement.setString(2, JavaDateToSQLDate(casted_item.getEnd_date()));
                statement.setInt(3, casted_item.getImports_count());
                statement.setLong(4, casted_item.getImports_amount());
                statement.setLong(5, casted_item.getMax_price());
                statement.setLong(6, casted_item.getMin_price());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean IsExistsEntity(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.importsummary where Summary_ID = ?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return false;
            int count = set.getInt(1);
            if (count != 1)
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean DeleteEntity(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM islabdb.importsummary WHERE Summary_ID = ?;");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean DeleteEntityList(Connection connection, Entity filter) {
        return false;
    }
    @Override
    public boolean EditEntity(Connection connection, Entity entity) {
        ImportSummary summary = (ImportSummary) entity;
        try {
            String sql_code =   "UPDATE islabdb.importsummary SET " +
                    "Summary_StartDate = ?, " +
                    "Summary_EndDate = ?, " +
                    "Summary_ImportsCount = ?, " +
                    "Summary_ImportsAmount = ?, " +
                    "Summary_MaxPrice = ?, " +
                    "Summary_MinPrice = ? " +
                    "WHERE Summary_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setString(1, JavaDateToSQLDate(summary.getStart_date()));
            statement.setString(2, JavaDateToSQLDate(summary.getEnd_date()));
            statement.setInt(3, summary.getImports_count());
            statement.setLong(4, summary.getImports_amount());
            statement.setLong(5, summary.getMax_price());
            statement.setLong(6, summary.getMin_price());
            statement.setLong(6, summary.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public long GetLastID(Connection connection) {
        return 0;
    }

    @Override
    public Entity createEntity() {
        return new ImportSummary();
    }
}
