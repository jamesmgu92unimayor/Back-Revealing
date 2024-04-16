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
@Table(name = "cliente_tipo_cocina", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientKitchenTypeEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClientEntity client;
    @ManyToOne
    @JoinColumn(name = "id_tipo_cocina")
    private KitchenTypeEntity kitchenType;
}
