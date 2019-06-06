package Utility;

import javax.servlet.ServletException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {
    public static String javaDateToSQLDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return "" + year + "-" + month + "-" + day + "";
    }
    public static Date sqlDateToJavaDate(String date) throws ServletException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date res = null;
        try {
            res = format.parse(date);
        } catch (ParseException e) {
            throw new ServletException(e.getMessage());
        }
        return res;
    }
}
