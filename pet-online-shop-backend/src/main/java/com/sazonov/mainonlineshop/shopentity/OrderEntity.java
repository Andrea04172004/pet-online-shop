package com.sazonov.mainonlineshop.shopentity;

import com.sazonov.mainonlineshop.enums.OrderStatus;
import com.sazonov.mainonlineshop.userentity.AddressEntity;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


@Entity
@Table(name = "orders")

public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    @Enumerated (EnumType.STRING)
    private OrderStatus status;

    @Column
    private double orderPrice;

    @OneToMany
    private List<LineItemEntity> lineItemEntitySet = new LinkedList<>();

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity userEntity; //fixme change to simple user name

    @Column
    private LocalDate created;

    @OneToOne
    private AddressEntity addressEntity;

    @OneToMany
    private List<PaymentEntity> paymentEntity;

    @OneToOne
    private DiscountEntity discountEntity;
}



