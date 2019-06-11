package lab2.Model;

public class Storage implements Entity {
    private long   id;
    private String name;
    private String description;

    public Storage() {
        this.id          = Entity.UNDEFINED_LONG;
        this.name        = Entity.UNDEFINED_STRING;
        this.description = Entity.UNDEFINED_STRING;
    }
    public Storage(long id, String name, String description) {
        this.id = id;
        if (name == null || name.equals(""))
            this.name = Entity.UNDEFINED_STRING;
        else this.name = name;
        if (description == null)
            this.description = Entity.UNDEFINED_STRING;
        else this.description = description;
    }

    @Override
    public long   getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}