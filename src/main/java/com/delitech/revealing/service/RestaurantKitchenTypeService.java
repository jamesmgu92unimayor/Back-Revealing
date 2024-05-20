package com.delitech.revealing.service;


import com.delitech.revealing.dto.RestaurantKitchenTypeDto;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface RestaurantKitchenTypeService {

    RestaurantKitchenTypeDto add(UUID userId, UUID typeId, Locale locale);

    void remove(UUID id, Locale locale);

    List<RestaurantKitchenTypeDto> getAllByRestaurant(UUID userId, Locale locale);

}
