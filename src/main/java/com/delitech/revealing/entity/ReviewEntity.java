package com.delitech.revealing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "resenas", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_restaurante")
    private RestaurantEntity restaurant;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClientEntity client;
    @Column(name = "puntuacion")
    private Double score;
    @Column(name = "comentario")
    private String comment;
    @Column(name = "fecha")
    private LocalDateTime dateTime;
}
