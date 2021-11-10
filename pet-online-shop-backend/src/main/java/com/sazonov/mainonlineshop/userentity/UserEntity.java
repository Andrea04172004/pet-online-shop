package com.sazonov.mainonlineshop.userentity;


import com.sazonov.mainonlineshop.shopentity.CartEntity;
import com.sazonov.mainonlineshop.shopentity.OrderEntity;
import com.sazonov.mainonlineshop.shopentity.WishListEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter


@Entity
@Table(name = "user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column
    private String password;

    @Column(length = 30)
    private String firstName;

    @Column(length = 30)
    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_phone_numbers", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone_number")
    private List<String> phoneList = new ArrayList<>();

    private LocalDate created;

    private LocalDate updated;

    private LocalDate lastVisit;

    private String role;

    private boolean active;

    @OneToOne
    private CartEntity cartEntity;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userEntity")
    private Set<OrderEntity> orderEntitySet;

    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<CreditCardEntity> creditCardEntitySet;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<AddressEntity> addressEntitySet;

    @OneToMany (fetch = FetchType.EAGER)
    private Set<WishListEntity> wishListEntities;

    public List<String> getPhoneList() {
        return this.phoneList;
    }

    public List<String> addPhone(String phone) {
        this.phoneList.add(phone);
        return this.phoneList;
    }

}
