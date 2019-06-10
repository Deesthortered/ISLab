package Entity;

import java.util.Date;

public class ImportDocument implements Entity {
    private long   id;
    private long   providerId;
    private Date   importDate;
    private String description;

    public ImportDocument() {
        this.id          = Entity.UNDEFINED_LONG;
        this.providerId  = Entity.UNDEFINED_LONG;
        this.importDate  = Entity.UNDEFINED_DATE;
        this.description = Entity.UNDEFINED_STRING;
    }
    public ImportDocument(long id, long providerId, Date importDate, String description) {
        this.id = id;
        this.providerId = providerId;
        this.importDate = importDate;
        if (description == null)
            this.description = null;
        else this.description = description;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getProviderId() {
        return providerId;
    }
    public Date getImportDate() {
        return importDate;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}