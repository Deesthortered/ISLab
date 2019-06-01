package utility_package;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

class DateHandlerTest {

    @Test
    void javaDateToSQLDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        Date javaDate = cal.getTime();
        Assert.assertEquals(DateHandler.JavaDateToSQLDate(javaDate), "2000-2-2");
    }

    @Test
    void SQLDateToJavaDate() {
        String sql_date = "2000-2-2";
        Date javaDate = DateHandler.SQLDateToJavaDate(sql_date);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        Date javaDate2 = cal.getTime();

        Assert.assertEquals(javaDate, javaDate2);
    }

    @Test
    void getMonthBoundaries() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        Date javaDate1 = cal.getTime();

        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date javaDate2 = cal.getTime();

        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date javaDate3 = cal.getTime();

        Assert.assertEquals(DateHandler.getMonthBoundaries(javaDate1).getKey(), javaDate2);
        Assert.assertEquals(DateHandler.getMonthBoundaries(javaDate1).getValue(), javaDate3);
    }

    @Test
    void nextDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        Date javaDate1 = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 3);
        Date javaDate2 = cal.getTime();

        Assert.assertEquals(DateHandler.nextDay(javaDate1), javaDate2);
    }
}