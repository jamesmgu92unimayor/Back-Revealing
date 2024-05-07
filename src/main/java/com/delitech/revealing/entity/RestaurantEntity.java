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
    @Column(name = "id_usuario", nullable = false)
    private UUID userId;
    @Column(name = "id_categoria_restaurante")
    private UUID categoryRestaurantId;
    @Column(name = "direccion")
    private String address;
    @Column(name = "celular")
    private String cellular;
    @ManyToOne
    @JoinColumn(name = "id_categoria_restaurante", insertable = false, updatable = false)
    private CategoryRestaurantEntity categoryRestaurant;
}
