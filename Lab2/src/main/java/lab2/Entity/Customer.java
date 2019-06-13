package lab2.Entity;

import lombok.*;

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

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
}