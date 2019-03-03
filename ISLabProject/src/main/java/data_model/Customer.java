package data_model;

public class Customer {
    private long   id;
    private String name;
    private String country;
    private String description;

    public Customer(long id, String name, String country, String description) {
        this.id = id;
        this.name = name;
        this.country = country;
        if (description == null || description.equals(""))
            this.description = null;
        else this.description = description;
    }
}
