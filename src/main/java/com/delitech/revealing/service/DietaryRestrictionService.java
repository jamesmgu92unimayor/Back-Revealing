package com.delitech.revealing.service;


import com.delitech.revealing.dto.DietaryRestrictionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface DietaryRestrictionService {

    DietaryRestrictionDto save(DietaryRestrictionDto dto, Locale locale);

    DietaryRestrictionDto update(DietaryRestrictionDto dto, UUID id, Locale locale);

    DietaryRestrictionDto getById(UUID id, Locale locale);

    Page<DietaryRestrictionDto> getAll(Pageable pageable, Locale locale);

    List<DietaryRestrictionDto> getAll(Locale locale);

    void delete(UUID id, Locale locale);
}
