package lab2.Entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "exportgoods")
public class ExportGoods implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ExportGoods_ID")
    private long id;

    @Column(name = "ExportGoods_DocumentID")
    private long documentId;

    @Column(name = "ExportGoods_GoodsID")
    private long goodsId;

    @Column(name = "ExportGoods_GoodsCount")
    private long goodsCount;

    @Column(name = "ExportGoods_GoodsPrice")
    private long goodsPrice;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
}