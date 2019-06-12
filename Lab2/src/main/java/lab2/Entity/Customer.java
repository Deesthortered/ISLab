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
@Table(name = "customer")
public class Customer implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Customer_ID")
    private long   id;

    @Column(name = "Customer_Name")
    private String name;

    @Column(name = "Customer_Country")
    private String country;

    @Column(name = "Customer_Description")
    private String description;
}