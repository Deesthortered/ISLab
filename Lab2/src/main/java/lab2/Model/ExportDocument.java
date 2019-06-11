package lab2.Model;

import java.util.Date;

public class ExportDocument implements Entity {
    private long   id;
    private long   customerId;
    private Date   exportDate;
    private String description;

    public ExportDocument() {
        this.id          = Entity.UNDEFINED_LONG;
        this.customerId  = Entity.UNDEFINED_LONG;
        this.exportDate  = Entity.UNDEFINED_DATE;
        this.description = Entity.UNDEFINED_STRING;
    }
    public ExportDocument(long id, long customerId, Date exportDate, String description) {
        this.id = id;
        this.customerId = customerId;
        if (exportDate == null)
            this.exportDate = Entity.UNDEFINED_DATE;
        else this.exportDate = exportDate;
        if (description == null)
            this.description = Entity.UNDEFINED_STRING;
        else this.description = description;
    }

    @Override
    public long   getId() {
        return id;
    }
    public long   getCustomerId() {
        return customerId;
    }
    public Date   getExportDate() {
        return exportDate;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}