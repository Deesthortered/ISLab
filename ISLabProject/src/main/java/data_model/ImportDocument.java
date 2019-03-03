package data_model;

import java.util.Date;

public class ImportDocument {
    private long   id;
    private long   provider_id;
    private Date   importdate;
    private String description;

    public ImportDocument(long id, long provider_id, Date importdate, String description) {
        this.id = id;
        this.provider_id = provider_id;
        this.importdate = importdate;
        if (description == null || description.equals(""))
            this.description = null;
        else this.description = description;
    }
}
