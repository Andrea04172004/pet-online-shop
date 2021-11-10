package com.sazonov.mainonlineshop.userentity;

import lombok.*;
import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter



@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String buildingNumber;

    @Column
    private String apartmentNumber;

}
