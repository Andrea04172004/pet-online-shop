package com.sazonov.mainonlineshop.shopentity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Entity
public class WishListEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (unique = true)
    private String title;


    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductEntity> productEntities;
}
