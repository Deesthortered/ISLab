package database_package.dao_package;

import data_model.Entity;
import data_model.ExportSummary;
import utility_package.DateHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DAOExportSummary implements DAOAbstract {

    private static DAOAbstract instance;

    private DAOExportSummary() {

    }
    public static synchronized DAOAbstract getInstance() {
        if (instance == null) {
            instance = new DAOExportSummary();
        }
        return instance;
    }

    @Override
    public ArrayList<Entity> GetEntityList(Connection connection, Entity filter, boolean limited, int start_index, int count_of_records) {
        ExportSummary casted_filter = (ExportSummary) filter;
        ArrayList<Entity> result = new ArrayList<>();

        try {
            String sql_query = "SELECT * FROM islabdb.exportsummary " +
                      "WHERE (Summary_ID = ?            OR ? = "   + Entity.undefined_long + ") AND " +
                            "(Summary_StartDate = ?     OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                            "(Summary_EndDate = ?       OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                            "(Summary_ExportsCount = ?  OR ? = "   + Entity.undefined_int  + ") AND " +
                            "(Summary_ExportsAmount = ? OR ? = "   + Entity.undefined_long + ") AND " +
                            "(Summary_MaxPrice = ?      OR ? = "   + Entity.undefined_long + ") AND " +
                            "(Summary_MinPrice = ?      OR ? = "   + Entity.undefined_long + ")" +
                    ( limited ? " limit ? offset ?" : "");

            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setString(3, DateHandler.JavaDateToSQLDate(casted_filter.getStart_date()));
            statement.setString(4, DateHandler.JavaDateToSQLDate(casted_filter.getStart_date()));
            statement.setString(5, DateHandler.JavaDateToSQLDate(casted_filter.getEnd_date()));
            statement.setString(6, DateHandler.JavaDateToSQLDate(casted_filter.getEnd_date()));
            statement.setInt(7, casted_filter.getExports_count());
            statement.setInt(8, casted_filter.getExports_count());
            statement.setLong(9, casted_filter.getExports_amount());
            statement.setLong(10, casted_filter.getExports_amount());
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
                int count = resultSet.getInt("Summary_ExportsCount");
                long amount = resultSet.getLong("Summary_ExportsAmount");
                long max_price = resultSet.getLong("Summary_MaxPrice");
                long min_price = resultSet.getLong("Summary_MinPrice");
                result.add(new ExportSummary(id, start_date, end_date, count, amount, max_price, min_price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public boolean AddEntityList(Connection connection, ArrayList<Entity> list) {
        for (Entity item : list) {
            ExportSummary casted_item = (ExportSummary) item;
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO islabdb.exportsummary (Summary_StartDate, Summary_EndDate, Summary_ExportsCount, Summary_ExportsAmount, Summary_MaxPrice, Summary_MinPrice) VALUES (?, ?, ?, ?, ?, ?);");
                statement.setString(1, DateHandler.JavaDateToSQLDate(casted_item.getStart_date()));
                statement.setString(2, DateHandler.JavaDateToSQLDate(casted_item.getEnd_date()));
                statement.setInt(3, casted_item.getExports_count());
                statement.setLong(4, casted_item.getExports_amount());
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
    public boolean DeleteEntityList(Connection connection, Entity filter) {
        try {
            ExportSummary casted_filter = (ExportSummary) filter;
            String sql_query = "DELETE FROM islabdb.exportsummary " +
                    "WHERE (Summary_ID = ?            OR ? = "   + Entity.undefined_long + ") AND " +
                    "(Summary_StartDate = ?     OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                    "(Summary_EndDate = ?       OR ? = \'" + DateHandler.JavaDateToSQLDate(Entity.undefined_date) + "\') AND " +
                    "(Summary_ExportsCount = ?  OR ? = "   + Entity.undefined_int  + ") AND " +
                    "(Summary_ExportsAmount = ? OR ? = "   + Entity.undefined_long + ") AND " +
                    "(Summary_MaxPrice = ?      OR ? = "   + Entity.undefined_long + ") AND " +
                    "(Summary_MinPrice = ?      OR ? = "   + Entity.undefined_long + ");";
            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setLong(1, casted_filter.getId());
            statement.setLong(2, casted_filter.getId());
            statement.setString(3, DateHandler.JavaDateToSQLDate(casted_filter.getStart_date()));
            statement.setString(4, DateHandler.JavaDateToSQLDate(casted_filter.getStart_date()));
            statement.setString(5, DateHandler.JavaDateToSQLDate(casted_filter.getEnd_date()));
            statement.setString(6, DateHandler.JavaDateToSQLDate(casted_filter.getEnd_date()));
            statement.setInt(7, casted_filter.getExports_count());
            statement.setInt(8, casted_filter.getExports_count());
            statement.setLong(9, casted_filter.getExports_amount());
            statement.setLong(10, casted_filter.getExports_amount());
            statement.setLong(11, casted_filter.getMax_price());
            statement.setLong(12, casted_filter.getMax_price());
            statement.setLong(13, casted_filter.getMin_price());
            statement.setLong(14, casted_filter.getMin_price());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean IsExistsEntity(Connection connection, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) from islabdb.exportsummary where Summary_ID = ?");
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
    public boolean EditEntity(Connection connection, Entity entity) {
        ExportSummary summary = (ExportSummary) entity;
        try {
            String sql_code =   "UPDATE islabdb.exportsummary SET " +
                    "Summary_StartDate = ?, " +
                    "Summary_EndDate = ?, " +
                    "Summary_ExportsCount = ?, " +
                    "Summary_ExportsAmount = ?, " +
                    "Summary_MaxPrice = ?, " +
                    "Summary_MinPrice = ? " +
                    "WHERE Summary_ID = ?;";

            PreparedStatement statement = connection.prepareStatement(sql_code);
            statement.setString(1, DateHandler.JavaDateToSQLDate(summary.getStart_date()));
            statement.setString(2, DateHandler.JavaDateToSQLDate(summary.getEnd_date()));
            statement.setInt(3, summary.getExports_count());
            statement.setLong(4, summary.getExports_amount());
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
        long res = -1;
        try {
            String sql_code = "SELECT max(Summary_ID) FROM islabdb.exportsummary;";
            PreparedStatement statement = connection.prepareStatement(sql_code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Entity createEntity() {
        return new ExportSummary();
    }

    public ArrayList<ExportSummary> GetSummaryBetweenDates(Connection connection, Date from, Date to) {
        ArrayList<ExportSummary> result = new ArrayList<>();
        try {
            String sql_query =
                    "SELECT * " +
                            "FROM islabdb.exportsummary " +
                            "WHERE ? <= Summary_StartDate AND Summary_EndDate < ?";
            PreparedStatement statement = connection.prepareStatement(sql_query);
            statement.setString(1, DateHandler.JavaDateToSQLDate(from));
            statement.setString(2, DateHandler.JavaDateToSQLDate(to));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("Summary_ID");
                Date start_date = resultSet.getDate("Summary_StartDate");
                Date end_date = resultSet.getDate("Summary_EndDate");
                int count = resultSet.getInt("Summary_ExportsCount");
                long amount = resultSet.getLong("Summary_ExportsAmount");
                long max_price = resultSet.getLong("Summary_MaxPrice");
                long min_price = resultSet.getLong("Summary_MinPrice");
                result.add(new ExportSummary(id, start_date, end_date, count, amount, max_price, min_price));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}