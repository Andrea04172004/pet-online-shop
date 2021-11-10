package com.sazonov.mainonlineshop.shopentity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder


@Entity
@Table(name = "cart")

public class CartEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<LineItemEntity> lineItemEntitySet = new HashSet<>();

}
