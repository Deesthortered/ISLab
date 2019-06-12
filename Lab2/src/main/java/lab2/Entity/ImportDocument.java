package lab2.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "importdocument")
public class ImportDocument implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Document_ID")
    private long   id;

    @Column(name = "Document_ProviderID")
    private long   providerId;

    @Column(name = "Document_ImportDate")
    private Date   importDate;

    @Column(name = "Document_Description")
    private String description;
}