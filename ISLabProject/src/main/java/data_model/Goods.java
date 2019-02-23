package data_model;

public class Goods {
    private long   id;
    private String name;
    private long average_price;

    public Goods(long id, String name, long average_price) {
        this.id = id;
        this.name = name;
        this.average_price = average_price;
    }
}
