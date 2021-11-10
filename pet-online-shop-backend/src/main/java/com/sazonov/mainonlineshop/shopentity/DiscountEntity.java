package com.sazonov.mainonlineshop.shopentity;

import com.sazonov.mainonlineshop.enums.DiscountTypes;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "discount")
public class DiscountEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private double percent;

    @Column
    private LocalDateTime expiration;

//    @Enumerated (EnumType.STRING)
//    private DiscountTypes discountTypes;

}
