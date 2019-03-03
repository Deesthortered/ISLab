package data_model;

public class Storage {
    private long   id;
    private String name;
    private String description;

    public Storage(long id, String name, String description) {
        this.id = id;
        this.name = name;
        if (description == null || description.equals(""))
            this.description = null;
        else this.description = description;
    }
}
