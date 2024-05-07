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
public class RestaurantDto implements Serializable {
    private UUID userId;
    private UUID categoryRestaurantId;
    private String address;
    private String cellular;
    private UserDto user;
    private CategoryRestaurantDto categoryRestaurant;
}
