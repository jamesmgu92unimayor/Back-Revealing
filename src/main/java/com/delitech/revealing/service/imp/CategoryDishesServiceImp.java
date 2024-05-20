package com.delitech.revealing.service.imp;

import com.delitech.revealing.dto.CategoryDishesDto;
import com.delitech.revealing.entity.CategoryDishesEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.CategoryDishesRepository;
import com.delitech.revealing.service.CategoryDishesService;
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
public class CategoryDishesServiceImp implements CategoryDishesService {

    private final MessageSource messageSource;
    private final CategoryDishesRepository repository;
    private final Function<CategoryDishesEntity, CategoryDishesDto> categoryDishesToDto;
    private final Function<CategoryDishesDto, CategoryDishesEntity> categoryDishesDtoToEntity;

    @Override
    @Transactional
    public CategoryDishesDto save(CategoryDishesDto dto, Locale locale) {
        return categoryDishesToDto.apply(repository.save(this.categoryDishesDtoToEntity.apply(dto)));
    }

    @Override
    @Transactional
    public CategoryDishesDto update(CategoryDishesDto dto, UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        entity.setName(dto.getName());
        return categoryDishesToDto.apply(repository.save(entity));
    }

    @Override
    public CategoryDishesDto getById(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return categoryDishesToDto.apply(entity);
    }

    @Override
    public Page<CategoryDishesDto> getAll(Pageable pageable, Locale locale) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    public List<CategoryDishesDto> getAll(Locale locale) {
        return repository.findAll().stream().map(this::convert).toList();
    }

    @Override
    @Transactional
    public void delete(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(entity.getId());
    }

    private CategoryDishesDto convert(CategoryDishesEntity entity) {
        return categoryDishesToDto.apply(entity);
    }

}
