package com.delitech.revealing.service;


import com.delitech.revealing.dto.CategoryRestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface CategoryRestaurantService {

    CategoryRestaurantDto save(CategoryRestaurantDto dto, Locale locale);

    CategoryRestaurantDto update(CategoryRestaurantDto dto, UUID id, Locale locale);

    CategoryRestaurantDto getById(UUID id, Locale locale);

    Page<CategoryRestaurantDto> getAll(Pageable pageable, Locale locale);

    List<CategoryRestaurantDto> getAll(Locale locale);

    void delete(UUID id, Locale locale);
}
