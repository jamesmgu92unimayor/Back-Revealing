package com.delitech.revealing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishesDto implements Serializable {
    private UUID id;
    private RestaurantDto restaurant;
    private String name;
    private String description;
    private BigDecimal price;
    private CategoryDishesDto categoryDishes;
}
