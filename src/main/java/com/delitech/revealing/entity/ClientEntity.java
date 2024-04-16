package com.delitech.revealing.entity;

import jakarta.persistence.*;
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
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UserEntity user;
}
