package com.sazonov.mainonlineshop.shopentity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime dateTime;
    @Column
    private String amount;
    @Column
    private boolean isCash;
    @Column
    private String clientName;
    @OneToOne
    private AttachmentEntity attachmentEntity;

}
