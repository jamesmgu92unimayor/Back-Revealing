package com.delitech.revealing.service;

import com.delitech.revealing.dto.DietaryRestrictionDto;
import com.delitech.revealing.entity.DietaryRestrictionEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.DietaryRestrictionRepository;
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
public class DietaryRestrictionServiceImp implements DietaryRestrictionService {

    private final MessageSource messageSource;
    private final DietaryRestrictionRepository repository;
    private final Function<DietaryRestrictionEntity, DietaryRestrictionDto> dietaryRestrictionToDto;
    private final Function<DietaryRestrictionDto, DietaryRestrictionEntity> dietaryRestrictionDtoToEntity;

    @Override
    @Transactional
    public DietaryRestrictionDto save(DietaryRestrictionDto dto, Locale locale) {
        return dietaryRestrictionToDto.apply(repository.save(this.dietaryRestrictionDtoToEntity.apply(dto)));
    }

    @Override
    @Transactional
    public DietaryRestrictionDto update(DietaryRestrictionDto dto, UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        entity.setName(dto.getName());
        return dietaryRestrictionToDto.apply(repository.save(entity));
    }

    @Override
    public DietaryRestrictionDto getById(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return dietaryRestrictionToDto.apply(entity);
    }

    @Override
    public Page<DietaryRestrictionDto> getAll(Pageable pageable, Locale locale) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    public List<DietaryRestrictionDto> getAll(Locale locale) {
        return repository.findAll().stream().map(this::convert).toList();
    }

    @Override
    @Transactional
    public void delete(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(entity.getId());
    }

    private DietaryRestrictionDto convert(DietaryRestrictionEntity entity) {
        return dietaryRestrictionToDto.apply(entity);
    }

}
