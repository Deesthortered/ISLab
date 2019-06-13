package lab2.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "availablegoods")
public class AvailableGoods implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Available_ID")
    private long    id;

    @Column(name = "Available_GoodsID")
    private long    goodsId;

    @Column(name = "Available_GoodsCount")
    private long    goodsCount;

    @Column(name = "Available_ProviderID")
    private long    providerId;

    @Column(name = "Available_StorageID")
    private long    storageId;

    @Column(name = "Available_Current")
    private boolean current;

    @Column(name = "Available_SnapshotDate")
    private Date    snapshotDate;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
}