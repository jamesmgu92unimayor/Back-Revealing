package com.delitech.revealing.service;


import com.delitech.revealing.dto.ClientCategoryRestaurantDto;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface ClientCategoryRestaurantService {

    ClientCategoryRestaurantDto add(UUID userId, UUID categoryId, Locale locale);

    void remove(UUID id, Locale locale);

    List<ClientCategoryRestaurantDto> getAllByClient(UUID userId, Locale locale);

}
