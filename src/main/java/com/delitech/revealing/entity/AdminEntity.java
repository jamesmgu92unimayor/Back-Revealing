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
@Table(name = "administradores", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminEntity implements Serializable {
    @Id
    @Column(name = "id_usuario", nullable = false)
    private UUID userId;
}
