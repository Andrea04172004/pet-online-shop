package com.sazonov.mainonlineshop.shopentity;

import com.sazonov.mainonlineshop.enums.ProductLabel;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@RequiredArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column
    @ElementCollection (fetch = FetchType.EAGER)
    private List<String> image = new LinkedList<>();

    @Column
    private LocalDate expirationDate;

    @Column (nullable = false)
    private int quantity;

    @ManyToOne
    private CategoryEntity category;

    @Enumerated (EnumType.STRING)
    private ProductLabel productLabel;
}
