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
@Table(name = "importgoods")
public class ImportGoods implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ImportGoods_ID")
    private long id;

    @Column(name = "ImportGoods_DocumentID")
    private long documentId;

    @Column(name = "ImportGoods_GoodsID")
    private long goodsId;

    @Column(name = "ImportGoods_GoodsCount")
    private long goodsCount;

    @Column(name = "ImportGoods_GoodsPrice")
    private long goodsPrice;
}