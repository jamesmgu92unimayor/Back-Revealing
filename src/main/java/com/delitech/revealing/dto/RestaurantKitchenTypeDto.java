package com.delitech.revealing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantKitchenTypeDto implements Serializable {
    private UUID userId;
    private RestaurantDto restaurant;
    private KitchenTypeDto kitchenType;
}
