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
@Table(name = "importmovedocument")
public class ImportMoveDocument implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Document_ID")
    private long id;

    @Column(name = "Document_ImportGoodsID")
    private long importGoodsId;

    @Column(name = "Document_StorageID")
    private long storageId;
}