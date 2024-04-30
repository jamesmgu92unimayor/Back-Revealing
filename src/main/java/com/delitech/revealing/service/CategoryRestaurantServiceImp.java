package com.delitech.revealing.service;

import com.delitech.revealing.dto.CategoryRestaurantDto;
import com.delitech.revealing.entity.CategoryRestaurantEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.CategoryRestaurantRepository;
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
public class CategoryRestaurantServiceImp implements CategoryRestaurantService {

    private final MessageSource messageSource;
    private final CategoryRestaurantRepository repository;
    private final Function<CategoryRestaurantEntity, CategoryRestaurantDto> categoryRestaurantToDto;
    private final Function<CategoryRestaurantDto, CategoryRestaurantEntity> categoryRestaurantDtoToEntity;

    @Override
    @Transactional
    public CategoryRestaurantDto save(CategoryRestaurantDto dto, Locale locale) {
        return categoryRestaurantToDto.apply(repository.save(this.categoryRestaurantDtoToEntity.apply(dto)));
    }

    @Override
    @Transactional
    public CategoryRestaurantDto update(CategoryRestaurantDto dto, UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        entity.setName(dto.getName());
        return categoryRestaurantToDto.apply(repository.save(entity));
    }

    @Override
    public CategoryRestaurantDto getById(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return categoryRestaurantToDto.apply(entity);
    }

    @Override
    public Page<CategoryRestaurantDto> getAll(Pageable pageable, Locale locale) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    public List<CategoryRestaurantDto> getAll(Locale locale) {
        return repository.findAll().stream().map(this::convert).toList();
    }

    @Override
    @Transactional
    public void delete(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(entity.getId());
    }

    private CategoryRestaurantDto convert(CategoryRestaurantEntity entity) {
        return categoryRestaurantToDto.apply(entity);
    }

}
