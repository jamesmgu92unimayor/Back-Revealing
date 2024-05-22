package com.delitech.revealing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRecommenderDto implements Serializable {
    private UUID id;
    private String name;
    private String email;
    private String address;
    private String cellular;
    private Double score;
    private CategoryRestaurantDto categoryRestaurant;

    private List<KitchenTypeDto> kitchenTypes = new ArrayList<>();
    private List<DishesRecommenderDto> dishes = new ArrayList<>();
}
