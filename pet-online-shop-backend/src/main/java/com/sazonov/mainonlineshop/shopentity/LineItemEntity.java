package com.sazonov.mainonlineshop.shopentity;


import lombok.*;
import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "lineItem")



public class LineItemEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private int quantity;



    @ManyToOne
    private ProductEntity product;



    public void increaseAmount() {

        this.quantity++;

    }

}
