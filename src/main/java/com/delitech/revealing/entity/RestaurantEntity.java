package com.delitech.revealing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
