package com.delitech.revealing.service;


import com.delitech.revealing.dto.KitchenTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface KitchenTypeService {

    KitchenTypeDto save(KitchenTypeDto dto, Locale locale);

    KitchenTypeDto update(KitchenTypeDto dto, UUID id, Locale locale);

    KitchenTypeDto getById(UUID id, Locale locale);

    Page<KitchenTypeDto> getAll(Pageable pageable, Locale locale);

    List<KitchenTypeDto> getAll(Locale locale);

    void delete(UUID id, Locale locale);
}
