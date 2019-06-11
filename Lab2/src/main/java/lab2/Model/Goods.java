package lab2.Model;

public class Goods implements Entity {
    private long   id;
    private String name;
    private long   averagePrice;
    private String description;

    public Goods() {
        this.id           = Entity.UNDEFINED_LONG;
        this.name         = Entity.UNDEFINED_STRING;
        this.averagePrice = Entity.UNDEFINED_LONG;
        this.description  = Entity.UNDEFINED_STRING;
    }
    public Goods(long id, String name, long averagePrice, String description) {
        this.id = id;
        if (name == null || name.equals(""))
            this.name = Entity.UNDEFINED_STRING;
        else this.name = name;
        this.averagePrice = averagePrice;
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
    public long   getAveragePrice() {
        return averagePrice;
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
    public void setAveragePrice(long averagePrice) {
        this.averagePrice = averagePrice;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}