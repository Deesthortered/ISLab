package utility_package;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {
    public static String JavaDateToSQLDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return "" + year + "-" + month + "-" + day + "";
    }
    public static Date SQLDateToJavaDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date res = null;
        try {
            res = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int getMonthsDifference(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date1);
        cal2.setTime(date2);

        int m1 = cal1.get(Calendar.YEAR) * 12 + cal1.get(Calendar.MONTH);
        int m2 = cal2.get(Calendar.YEAR) * 12 + cal2.get(Calendar.MONTH);
        return m2 - m1 + 1;
    }
    public static Pair<Date, Date> getMonthBoundaries(Date date) {
        Calendar cal_now  = Calendar.getInstance();
        Calendar cal_before  = Calendar.getInstance();
        Calendar cal_after = Calendar.getInstance();

        cal_now.setTime(date);

        cal_before.set(Calendar.YEAR, cal_now.get(Calendar.YEAR));
        cal_before.set(Calendar.MONTH, cal_now.get(Calendar.MONTH));
        cal_before.set(Calendar.DAY_OF_MONTH, cal_now.getActualMinimum(Calendar.DAY_OF_MONTH));

        cal_after.set(Calendar.YEAR, cal_now.get(Calendar.YEAR));
        cal_after.set(Calendar.MONTH, cal_now.get(Calendar.MONTH));
        cal_after.add(Calendar.MONTH, 1);
        cal_after.set(Calendar.DAY_OF_MONTH, cal_now.getActualMinimum(Calendar.DAY_OF_MONTH));

        return new Pair<>(cal_before.getTime(), cal_after.getTime());
    }
    public static Date nextDay(Date date) {
        Calendar cal  = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
}
