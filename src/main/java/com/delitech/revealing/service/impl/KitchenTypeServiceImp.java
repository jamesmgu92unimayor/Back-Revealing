package com.delitech.revealing.service.impl;

import com.delitech.revealing.dto.KitchenTypeDto;
import com.delitech.revealing.entity.KitchenTypeEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.KitchenTypeRepository;
import com.delitech.revealing.service.KitchenTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_NOTFOUND;

@Service
@RequiredArgsConstructor
public class KitchenTypeServiceImp implements KitchenTypeService {

    private final MessageSource messageSource;
    private final KitchenTypeRepository repository;
    private final Function<KitchenTypeEntity, KitchenTypeDto> kitchenTypeToDto;
    private final Function<KitchenTypeDto, KitchenTypeEntity> kitchenTypeDtoToEntity;

    @Override
    @Transactional
    public KitchenTypeDto save(KitchenTypeDto dto, Locale locale) {
        return kitchenTypeToDto.apply(repository.save(this.kitchenTypeDtoToEntity.apply(dto)));
    }

    @Override
    @Transactional
    public KitchenTypeDto update(KitchenTypeDto dto, UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        entity.setName(dto.getName());
        return kitchenTypeToDto.apply(repository.save(entity));
    }

    @Override
    public KitchenTypeDto getById(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return kitchenTypeToDto.apply(entity);
    }

    @Override
    public Page<KitchenTypeDto> getAll(Pageable pageable, Locale locale) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    public List<KitchenTypeDto> getAll(Locale locale) {
        return repository.findAll().stream().map(this::convert).toList();
    }

    @Override
    @Transactional
    public void delete(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(entity.getId());
    }

    private KitchenTypeDto convert(KitchenTypeEntity entity) {
        return kitchenTypeToDto.apply(entity);
    }

}
