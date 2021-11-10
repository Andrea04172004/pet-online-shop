package com.sazonov.mainonlineshop;

import com.sazonov.mainonlineshop.enums.OrderStatus;
import com.sazonov.mainonlineshop.enums.ProductLabel;
import com.sazonov.mainonlineshop.repository.*;
import com.sazonov.mainonlineshop.shopentity.*;
import com.sazonov.mainonlineshop.enums.Roles;
import com.sazonov.mainonlineshop.userentity.AddressEntity;
import com.sazonov.mainonlineshop.userentity.CreditCardEntity;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import com.sazonov.mainonlineshop.utils.UserGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication()
public class MainonlineshopApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MainonlineshopApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, OrderRepository orderRepository, CreditCardRepository creditCardRepository,
                           AddressRepository addressRepository, DiscountRepository discountRepository, AttachmentRepository attachmentRepository,
                           CartRepository cartRepository, CategoryRepository categoryRepository, PaymentRepository paymentRepository,
                           ProductRepository productRepository, PasswordEncoder passwordEncoder, LineItemRepository lineItemRepository,
                           WishListRepository wishListRepository) {

        return args -> {

            UserEntity userEntity = new UserEntity();

            Set<CreditCardEntity> creditCardEntitiesAdmin = new HashSet<>();

            CreditCardEntity creditCardEntity4 = CreditCardEntity.builder()
                    .cardNumber("1111-1111-1111-1111")
                    .expirationDate("2022")
                    .cardType("VISA")
                    .build();

            creditCardRepository.save(creditCardEntity4);

            CreditCardEntity creditCardEntity5 = CreditCardEntity.builder()
                    .cardNumber("2222-2222-2222-2222")
                    .expirationDate("2022")
                    .cardType("Master Card")
                    .build();

            creditCardRepository.save(creditCardEntity5);

            CreditCardEntity creditCardEntity6 = CreditCardEntity.builder()
                    .cardNumber("3333-3333-3333-3333")
                    .expirationDate("2022")
                    .cardType("American Express")
                    .build();

            creditCardRepository.save(creditCardEntity6);

            creditCardEntitiesAdmin.add(creditCardEntity4);
            creditCardEntitiesAdmin.add(creditCardEntity5);
            creditCardEntitiesAdmin.add(creditCardEntity6);

            creditCardRepository.saveAll(creditCardEntitiesAdmin);


            UserEntity admin = UserEntity.builder()
                    .email("admin@mail.com")
                    .password(passwordEncoder.encode("123"))
                    .firstName("admin")
                    .lastName("adminov")
                    .phoneList(userEntity.addPhone("+38 068 2 864 111"))
                    .phoneList(userEntity.addPhone("+38 068 2 864 222"))
                    .phoneList(userEntity.addPhone("+38 068 2 864 333"))
                    .phoneList(userEntity.addPhone("+38 068 2 864 444"))
                    .phoneList(userEntity.addPhone("+38 068 2 864 555"))
                    //  .phoneNumber("+38 068 2 864 864")// setLocation("uk")
                    .active(true)
                    .created(LocalDate.now())
                    .updated(LocalDate.now())
                    .lastVisit(LocalDate.now())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .role(Roles.ROLE_ADMIN.name())
                    .build();

            userRepository.save(admin);


            AddressEntity addressEntity6 = AddressEntity.builder()
                    .city("Stol")
                    .street("Ruchka street")
                    .buildingNumber("1122")
                    .apartmentNumber("33f")
                    .build();
            addressRepository.save(addressEntity6);

            AddressEntity addressEntity7 = AddressEntity.builder()
                    .city("voda")
                    .street("kran Street")
                    .buildingNumber("8961")
                    .apartmentNumber("91h")
                    .build();
            addressRepository.save(addressEntity7);

            AddressEntity addressEntity8 = AddressEntity.builder()
                    .city("dohovka")
                    .street("lodka street")
                    .buildingNumber("182")
                    .apartmentNumber("53c")
                    .build();

            addressRepository.save(addressEntity8);

            Set<AddressEntity> addressEntitySet = new HashSet<>();
            addressEntitySet.add(addressEntity6);
            addressEntitySet.add(addressEntity7);
            addressEntitySet.add(addressEntity8);

            addressRepository.saveAll(addressEntitySet);
            admin.setAddressEntitySet(addressEntitySet);
            userRepository.save(admin);

            CreditCardEntity creditCardEntity1 = CreditCardEntity.builder()
                    .cardNumber("4024007161952808")
                    .cardType("VISA GOLD")
                    .expirationDate("08-11-2020").build();
            CreditCardEntity creditCardEntity2 = CreditCardEntity.builder()
                    .cardNumber("5293553709949443")
                    .cardType("MASTER CARD")
                    .expirationDate("08-11-2020").build();

            CreditCardEntity creditCardEntity3 = CreditCardEntity.builder()
                    .cardNumber("376893368247957")
                    .cardType("VISA")
                    .expirationDate("08-11-2020").build();

            creditCardRepository.save(creditCardEntity1);
            creditCardRepository.save(creditCardEntity2);
            creditCardRepository.save(creditCardEntity3);

            Set<CreditCardEntity> creditCardEntities = new HashSet<>();
            creditCardEntities.add(creditCardEntity1);
            creditCardEntities.add(creditCardEntity2);
            creditCardEntities.add(creditCardEntity3);

            admin.setCreditCardEntitySet(creditCardEntitiesAdmin);
            userRepository.save(admin);

            UserEntity andrewKachmar = UserEntity.builder()
                    .email("andrew@mail.com")
                    .password("$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu")
                    .firstName("andrew")
                    .lastName("kachmar")
                    .phoneList(userEntity.addPhone("+38 068 2 864 864"))
                    //  .phoneNumber("+38 068 2 864 864")// setLocation("uk")
                    .active(true)
                    .created(LocalDate.now())
                    .updated(LocalDate.now())
                    .lastVisit(LocalDate.now())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .role(Roles.ROLE_CUSTOMER.name())
                    .creditCardEntitySet(creditCardEntities)
                    .build();

            userRepository.save(andrewKachmar);


            UserEntity manager = UserEntity.builder()
                    .email("manager@mail.com")
                    .password("$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu")
                    .firstName("manager")
                    .lastName("managerov")
                    .phoneList(userEntity.addPhone("+27 124 6 342 123"))
                    //.phoneNumber("+27 124 6 342 123")
                    .active(true)
                    .created(LocalDate.now())
                    .updated(LocalDate.now())
                    .lastVisit(LocalDate.now())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .role(Roles.ROLE_MANAGER.name())
                    .build();

            userRepository.save(manager);


            UserEntity testUser = UserEntity.builder()
                    .email("testUser@gmail.com")
                    .password("$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu")
                    .firstName("Tommy")
                    .lastName("Tester")
                    .phoneList(userEntity.addPhone("+27 777 7 777 666"))
                    .phoneList(userEntity.addPhone("+27 777 7 777 777"))
                    //.phoneNumber("+27 124 6 342 123")
                    .active(true)
                    .created(LocalDate.now())
                    .updated(LocalDate.now())
                    .lastVisit(LocalDate.now())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .role(Roles.ROLE_CUSTOMER.name())
                    .build();

            userRepository.save(testUser);


            UserEntity kirillSazonov = UserEntity.builder()
                    .email("kirill@mail.com")
                    .password("$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu")
                    .firstName("kirill")
                    .lastName("sazonov")
                    .phoneList(userEntity.addPhone("+38 068 2 999 777"))
                    .phoneList(userEntity.addPhone("+27 777 7 777 888"))
                    .phoneList(userEntity.addPhone("+27 777 7 777 999"))
                    .phoneList(userEntity.addPhone("+27 777 7 777 000"))
                    //  .phoneNumber("+38 068 2 864 864")// setLocation("uk")
                    .active(true)
                    .created(LocalDate.now())
                    .updated(LocalDate.now())
                    .lastVisit(LocalDate.now())
                    .cartEntity(cartRepository.save(new CartEntity()))
                    .role(Roles.ROLE_ADMIN.name())
                    .build();

            userRepository.save(kirillSazonov);

            AddressEntity addressEntity3 = AddressEntity.builder()
                    .city("Katok")
                    .street("Buhanka street")
                    .buildingNumber("3214")
                    .apartmentNumber("21z")
                    .build();
            addressRepository.save(addressEntity3);

            AddressEntity addressEntity4 = AddressEntity.builder()
                    .city("Shalash")
                    .street("Derevo Street")
                    .buildingNumber("2239")
                    .apartmentNumber("2c")
                    .build();
            addressRepository.save(addressEntity4);

            AddressEntity addressEntity5 = AddressEntity.builder()
                    .city("Bashmak")
                    .street("tarelka street")
                    .buildingNumber("6691")
                    .apartmentNumber("88a")
                    .build();
            addressRepository.save(addressEntity5);

            Set<AddressEntity> addressEntityKirillSet = new HashSet<>();
            addressEntityKirillSet.add(addressEntity3);
            addressEntityKirillSet.add(addressEntity4);
            addressEntityKirillSet.add(addressEntity5);

            addressRepository.saveAll(addressEntityKirillSet);

            kirillSazonov.setAddressEntitySet(addressEntityKirillSet);
            userRepository.save(kirillSazonov);


            for (int i = 0; i < 4; i++) {

                UserEntity requestGenerateCustomers = UserEntity.builder()
                        .email(UserGenerator.setEmailAddress())
                        .password(passwordEncoder.encode(UserGenerator.setPassword()))
                        .firstName(UserGenerator.setFirstName())
                        .lastName(UserGenerator.setLastName())
                        .phoneList(userEntity.addPhone(UserGenerator.setPhoneNumber()))
                        .active(true)
                        .created(LocalDate.now())
                        .updated(LocalDate.now())
                        .lastVisit(LocalDate.now())
                        .cartEntity(cartRepository.save(new CartEntity()))
                        .role(Roles.ROLE_CUSTOMER.name())
                        .build();

                userRepository.save(requestGenerateCustomers);
            }

            CategoryEntity generalCategory = CategoryEntity.builder()
                    .name("GeneralCategory")
                    .productSet(new HashSet<>())
                    .build();
            categoryRepository.save(generalCategory);

            CategoryEntity fruit = CategoryEntity.builder()
                    .name("fruit")
                    .build();

            CategoryEntity vegetable = CategoryEntity.builder()
                    .name("vegetable")
                    .build();

            CategoryEntity meat = CategoryEntity.builder()
                    .name("meat")
                    .build();

            categoryRepository.save(fruit);
            categoryRepository.save(vegetable);
            categoryRepository.save(meat);

            CategoryEntity appleCategory = CategoryEntity.builder()
                    .name("appleCategory")
                    .productSet(new HashSet<>())
                    .build();

            categoryRepository.save(appleCategory);
            Set<CategoryEntity> subCategories = Set.of(appleCategory);
            //fruit.setSubCategories(subCategories);
            categoryRepository.save(fruit);

            List<String> images = new LinkedList<>();
            images.add("https://eshop.elit.ua/imgbank/Image/masljniy_filtr.jpg");
            images.add("https://bezdor4x4.com.ua/uploads/content/2018/12/19/source/3-27388x.jpg");

            List<String> images2 = new LinkedList<>();
            images2.add("https://dvizhok.su/assets/images/resources/15187/ysilennoe-sceplenue.jpg");
            images2.add("https://kitaec.ua/upload/iblock/e08/Kupplung_smart_451_m.JPG");

            List<String> images3 = new LinkedList<>();
            images3.add("https://a.d-cd.net/b3a86d4s-960.jpg");
            images3.add("https://d1350.com/wa-data/public/shop/products/06/80/118006/images/56013/56013.750@2x.jpg");


            ProductEntity apple = ProductEntity.builder()
                    .name("apple")
                    .price(1.11)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .image(images)
                    .quantity(12)
                    .productLabel(ProductLabel.NEW)
                    .category(appleCategory)
                    .build();

            ProductEntity apple1 = ProductEntity.builder()
                    .name("apple1")
                    .price(1.11)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .image(images2)
                    .quantity(13)
                    .productLabel(ProductLabel.NEW)
                    .category(fruit)
                    .build();

            ProductEntity apple2 = ProductEntity.builder()
                    .name("apple2")
                    .price(1.11)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .image(images3)
                    .quantity(56)
                    .productLabel(ProductLabel.NEW)
                    .category(fruit)
                    .build();

            ProductEntity apple3 = ProductEntity.builder()
                    .name("apple3")
                    .price(1.11)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .image(images)
                    .quantity(122)
                    .productLabel(ProductLabel.NEW)
                    .category(fruit)
                    .build();

            ProductEntity tomato = ProductEntity.builder()
                    .name("tomato")
                    .price(2.22)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .image(images2)
                    .quantity(100)
                    .productLabel(ProductLabel.NEW)
                    .category(vegetable)
                    .build();

            ProductEntity pork = ProductEntity.builder()
                    .name("pork1")
                    .price(3.33)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .image(images3)
                    .productLabel(ProductLabel.NEW)
                    .quantity(18)
                    .category(meat)
                    .build();

            ProductEntity pork1 = ProductEntity.builder()
                    .name("pork2")
                    .price(3.33)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .image(images)
                    .productLabel(ProductLabel.NEW)
                    .quantity(18)
                    .category(meat)
                    .build();

            ProductEntity pork2 = ProductEntity.builder()
                    .name("pork3")
                    .price(3.33)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .image(images2)
                    .productLabel(ProductLabel.NEW)
                    .quantity(18)
                    .category(meat)
                    .build();

            ProductEntity pork3 = ProductEntity.builder()
                    .name("pork4")
                    .price(3.33)
                    .expirationDate(LocalDate.now().plusDays(100))
                    .image(images3)
                    .productLabel(ProductLabel.NEW)
                    .quantity(18)
                    .category(meat)
                    .build();
            productRepository.save(apple);
            productRepository.save(apple1);
            productRepository.save(apple2);
            productRepository.save(apple3);
            productRepository.save(tomato);
            productRepository.save(pork);
            productRepository.save(pork1);
            productRepository.save(pork2);
            productRepository.save(pork3);

            appleCategory.getProductSet().add(apple);
            categoryRepository.save(appleCategory);


            AddressEntity addressEntity = AddressEntity.builder()
                    .apartmentNumber("56")
                    .buildingNumber("23")
                    .city("Tylchin")
                    .street("Joid").build();
            addressRepository.save(addressEntity);

            DiscountEntity discountEntity = DiscountEntity.builder()
                    .expiration(LocalDateTime.now())
                    .percent(12).build();
            discountRepository.save(discountEntity);


            LineItemEntity lineItemEntity1 = LineItemEntity.builder()
                    .quantity(2)
                    .product(pork)
                    .build();

            LineItemEntity lineItemEntity2 = LineItemEntity.builder()
                    .quantity(1)
                    .product(pork1)
                    .build();

            LineItemEntity lineItemEntity3 = LineItemEntity.builder()
                    .quantity(2)
                    .product(pork3)
                    .build();

            lineItemRepository.save(lineItemEntity1);
            lineItemRepository.save(lineItemEntity2);
            lineItemRepository.save(lineItemEntity3);


            List<LineItemEntity> lines = new LinkedList<>();
            lines.add(lineItemEntity1);
            lines.add(lineItemEntity2);
            lines.add(lineItemEntity3);


            AttachmentEntity attachmentEntity1 = AttachmentEntity.builder()
                    .created(LocalDateTime.now())
                    .content("It cost a fortune")
                    .name("Test").build();
            AttachmentEntity attachmentEntity2 = AttachmentEntity.builder()
                    .created(LocalDateTime.now())
                    .content("It cost a fortune")
                    .name("Test1").build();
            AttachmentEntity attachmentEntity3 = AttachmentEntity.builder()
                    .created(LocalDateTime.now())
                    .content("It cost a fortune")
                    .name("Test2").build();

            attachmentRepository.save(attachmentEntity1);
            attachmentRepository.save(attachmentEntity2);
            attachmentRepository.save(attachmentEntity3);

            PaymentEntity paymentEntity1 = PaymentEntity.builder()
                    .isCash(false)
                    .amount("2232")
                    .clientName("Oleg")
                    .dateTime(LocalDateTime.now())
                    .attachmentEntity(attachmentEntity1).build();

            PaymentEntity paymentEntity2 = PaymentEntity.builder()
                    .isCash(false)
                    .amount("100")
                    .clientName("Dima")
                    .dateTime(LocalDateTime.now())
                    .attachmentEntity(attachmentEntity2).build();

            PaymentEntity paymentEntity3 = PaymentEntity.builder()
                    .isCash(true)
                    .amount("23")
                    .clientName("Max")
                    .dateTime(LocalDateTime.now())
                    .attachmentEntity(attachmentEntity3).build();

            paymentRepository.save(paymentEntity1);
            paymentRepository.save(paymentEntity2);
            paymentRepository.save(paymentEntity3);

            List<PaymentEntity> paymentEntities1 = new LinkedList<>();
            paymentEntities1.add(paymentEntity1);
            paymentEntities1.add(paymentEntity2);
            paymentEntities1.add(paymentEntity3);


            OrderEntity order1 = OrderEntity.builder()
                    .created(LocalDate.now())
                    .status(OrderStatus.NEW)
                    .orderPrice(1000)
                    .discountEntity(discountEntity)
                    .addressEntity(addressEntity)
                    .userEntity(admin)
                    .lineItemEntitySet(lines)
                    .paymentEntity(paymentEntities1).build();
            orderRepository.save(order1);

            OrderEntity order2 = OrderEntity.builder()
                    .created(LocalDate.now())
                    .status(OrderStatus.CLOSED)
                    .orderPrice(1000)
                    .discountEntity(discountEntity)
                    .addressEntity(addressEntity)
                    .userEntity(admin)
                    .lineItemEntitySet(new LinkedList<>())
                    .paymentEntity(new LinkedList<>()).build();
            orderRepository.save(order2);

            OrderEntity order3 = OrderEntity.builder()
                    .created(LocalDate.now())
                    .status(OrderStatus.PAID)
                    .orderPrice(1000)
                    .discountEntity(discountEntity)
                    .addressEntity(addressEntity)
                    .userEntity(admin)
                    .lineItemEntitySet(new LinkedList<>())
                    .paymentEntity(new LinkedList<>()).build();
            orderRepository.save(order3);

            OrderEntity order4 = OrderEntity.builder()
                    .created(LocalDate.now())
                    .status(OrderStatus.UNPAID)
                    .orderPrice(1000)
                    .discountEntity(discountEntity)
                    .addressEntity(addressEntity)
                    .userEntity(admin)
                    .lineItemEntitySet(new LinkedList<>())
                    .paymentEntity(new LinkedList<>()).build();
            orderRepository.save(order4);

            Set<OrderEntity> orderEntities = new HashSet<>();
            orderEntities.add(order1);
            orderEntities.add(order2);
            orderEntities.add(order3);

            orderRepository.saveAll(orderEntities);

            admin.setOrderEntitySet(orderEntities);
            userRepository.save(admin);



            List<ProductEntity> productEntities = new LinkedList<>();
            productEntities.add(apple);
            productEntities.add(apple1);
            productEntities.add(apple2);
            productEntities.add(apple3);



            List<ProductEntity> productEntities2 = new LinkedList<>();
            productEntities2.add(pork);
            productEntities2.add(pork1);
            productEntities2.add(pork2);
            productEntities2.add(pork3);



            WishListEntity wishListEntity = WishListEntity.builder()
                    .id(1)
                    .title("Products")
                    .productEntities(productEntities).build();
            wishListRepository.save(wishListEntity);

            WishListEntity wishListEntity2 = WishListEntity.builder()
                    .id(2)
                    .title("Items")
                    .productEntities(productEntities2).build();
            wishListRepository.save(wishListEntity2);

            Set<WishListEntity> wishListEntities = new HashSet<>();
            wishListEntities.add(wishListEntity);
            wishListEntities.add(wishListEntity2);

            admin.setWishListEntities(wishListEntities);
            userRepository.save(admin);
        };

    }
}
