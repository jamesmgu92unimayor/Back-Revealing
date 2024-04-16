package com.delitech.revealing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "administradores", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminEntity implements Serializable {
    @Id
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UserEntity user;
}
