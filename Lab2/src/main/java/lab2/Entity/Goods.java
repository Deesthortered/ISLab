package lab2.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}