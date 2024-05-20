package com.delitech.revealing.service;


import com.delitech.revealing.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface RestaurantService {

    RestaurantDto save(RestaurantDto dto, Locale locale);

    RestaurantDto update(RestaurantDto dto, UUID id, Locale locale);

    RestaurantDto getById(UUID id, Locale locale);

    Page<RestaurantDto> getAll(Pageable pageable, Locale locale);

    List<RestaurantDto> getAll(Locale locale);

    void delete(UUID id, Locale locale);
}
