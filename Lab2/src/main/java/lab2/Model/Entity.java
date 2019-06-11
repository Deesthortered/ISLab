package lab2.Model;

import java.util.Date;

public interface Entity {
    int    UNDEFINED_INT    = -1;
    long   UNDEFINED_LONG   = -1;
    String UNDEFINED_STRING = "____undefined____";
    Date   UNDEFINED_DATE   = new Date(Long.MIN_VALUE);

    long getId();
    void setId(long id);
}