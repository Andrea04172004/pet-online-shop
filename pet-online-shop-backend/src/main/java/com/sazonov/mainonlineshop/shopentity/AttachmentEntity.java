package com.sazonov.mainonlineshop.shopentity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = {"id"})
@Builder
@Getter @Setter
@Entity
@Table (name = "attachment")
public class AttachmentEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private LocalDateTime created;

    @Column
    private String content;



}
