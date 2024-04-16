package com.delitech.revealing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "restaurante_tipo_cocina", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantKitchenTypeEntity implements Serializable {
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
    @JoinColumn(name = "id_tipo_cocina")
    private KitchenTypeEntity kitchenType;
}
