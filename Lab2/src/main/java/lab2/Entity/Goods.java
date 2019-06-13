package lab2.Entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "goods")
public class Goods implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Goods_ID")
    private long   id;

    @Column(name = "Goods_Name")
    private String name;

    @Column(name = "Goods_AveragePrice")
    private long   averagePrice;

    @Column(name = "Goods_Description")
    private String description;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
}