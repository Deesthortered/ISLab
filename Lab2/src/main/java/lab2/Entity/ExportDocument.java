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
@Table(name = "exportdocument")
public class ExportDocument implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Document_ID")
    private long   id;

    @Column(name = "Document_CustomerID")
    private long   customerId;

    @Column(name = "Document_ExportDate")
    private Date   exportDate;

    @Column(name = "Document_Description")
    private String description;
}