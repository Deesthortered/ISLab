package data_model;

public class Goods {
    private long   id;
    private String name;
    private long   average_price;
    private String description;

    public Goods(long id, String name, long average_price, String description) {
        this.id = id;
        this.name = name;
        this.average_price = average_price;
        if (description == null || description.equals(""))
            this.description = null;
        else this.description = description;
    }
}
