package com.sazonov.mainonlineshop.shopentity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@Builder


@Entity
@Table(name = "category")



public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(nullable = false, unique = true)
    private String name;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<ProductEntity> productSet = new HashSet<>();




    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    private CategoryEntity parentCategory;



    public CategoryEntity(String category) {
    }
}
