package com.delitech.revealing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "restaurantes", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantEntity implements Serializable {
    @Id
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UserEntity user;
    @Column(name = "id_categoria_restaurante")
    private UUID categoryRestaurantId;
    @ManyToOne
    @JoinColumn(name = "id_categoria_restaurante", insertable = false, updatable = false)
    private CategoryRestaurantEntity categoryRestaurant;
}
