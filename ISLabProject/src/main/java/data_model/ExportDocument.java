package data_model;

import java.util.Date;

public class ExportDocument {
    private long   id;
    private long   customer_id;
    private Date   export_date;
    private String description;

    public ExportDocument(long id,long customer_id, Date export_date, String description) {
        this.id = id;
        this.customer_id = customer_id;
        this.export_date = export_date;
        if (description == null || description.equals(""))
            this.description = null;
        else this.description = description;
    }
}
