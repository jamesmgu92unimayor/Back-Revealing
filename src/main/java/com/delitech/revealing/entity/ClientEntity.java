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
@Table(name = "clientes", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEntity implements Serializable {
    @Id
    @Column(name = "id_usuario", nullable = false)
    private UUID userId;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastname;
    @Column(name = "celular")
    private String cellular;
    @Column(name = "id_imagen")
    private UUID imageId;
}
