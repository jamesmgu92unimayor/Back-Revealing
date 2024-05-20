package com.delitech.revealing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto implements Serializable {
    private String name;
    private String lastname;
    private String cellular;
    private UserDto user;

    private List<ClientCategoryRestaurantDto> categoryRestaurants = new ArrayList<>();
    private List<ClientDietaryRestrictionDto> dietaryRestrictions = new ArrayList<>();
    private List<ClientKitchenTypeDto> kitchenTypes = new ArrayList<>();
}
