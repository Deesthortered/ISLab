package lab2.Model;

public class Customer implements Entity {
    private long   id;
    private String name;
    private String country;
    private String description;

    public Customer() {
        this.id          = Entity.UNDEFINED_LONG;
        this.name        = Entity.UNDEFINED_STRING;
        this.country     = Entity.UNDEFINED_STRING;
        this.description = Entity.UNDEFINED_STRING;
    }
    public Customer(long id, String name, String country, String description) {
        this.id = id;

        if (name == null || name.equals(""))
            this.name = Entity.UNDEFINED_STRING;
        else this.name = name;

        if (country == null || country.equals(""))
            this.country = Entity.UNDEFINED_STRING;
        else this.country = country;

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
    public String getCountry() {
        return country;
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
    public void setCountry(String country) {
        this.country = country;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}