package com.delitech.revealing.service;


import com.delitech.revealing.dto.CategoryDishesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface CategoryDishesService {

    CategoryDishesDto save(CategoryDishesDto dto, Locale locale);

    CategoryDishesDto update(CategoryDishesDto dto, UUID id, Locale locale);

    CategoryDishesDto getById(UUID id, Locale locale);

    Page<CategoryDishesDto> getAll(Pageable pageable, Locale locale);

    List<CategoryDishesDto> getAll(Locale locale);

    void delete(UUID id, Locale locale);
}
