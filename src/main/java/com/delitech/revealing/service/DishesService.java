package com.delitech.revealing.service;


import com.delitech.revealing.dto.DishesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface DishesService {

    DishesDto save(DishesDto dto, UUID userId, Locale locale);

    DishesDto update(DishesDto dto, UUID id, Locale locale);

    DishesDto getById(UUID id, Locale locale);

    void delete(UUID id, Locale locale);

    Page<DishesDto> getAll(Pageable pageable, Locale locale);

    List<DishesDto> getAllByRestaurant(UUID userId, Locale locale);

    Page<DishesDto> getAllByRestaurant(UUID userId, Pageable pageable, Locale locale);

    List<DishesDto> getAllByRestaurantAndCategory(UUID userId, UUID categoryId, Locale locale);

    Page<DishesDto> getAllByCategory(UUID categoryId, Pageable pageable, Locale locale);

}
