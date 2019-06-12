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
@Table(name = "provider")
public class Provider implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Provider_ID")
    private long   id;

    @Column(name = "Provider_Name")
    private String name;

    @Column(name = "Provider_Country")
    private String country;

    @Column(name = "Provider_Description")
    private String description;
}