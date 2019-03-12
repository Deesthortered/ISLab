package data_model;

public class Provider {
    private long   id;
    private String name;
    private String country;
    private String description;
    private long ID;

    public Provider(long id, String name, String country, String description) {
        this.id = id;
        this.name = name;
        this.country = country;
        if (description == null || description.equals(""))
            this.description = null;
        else this.description = description;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCountry() {
        return country;
    }
    public String getDescription() {
        return description;
    }
}
