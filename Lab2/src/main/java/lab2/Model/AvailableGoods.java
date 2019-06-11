package lab2.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableGoods implements Entity {
    private long    id;
    private long    goodsId;
    private long    goodsCount;
    private long    providerId;
    private long    storageId;
    private boolean current;
    private Date    snapshotDate;

    @Override
    public long getId() {
        return this.id;
    }
    @Override
    public void setId(long id) {
        this.id = id;
    }
}