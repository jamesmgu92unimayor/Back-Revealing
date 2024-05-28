package com.delitech.revealing.service.impl;

import com.delitech.revealing.dto.CategoryDishesDto;
import com.delitech.revealing.dto.DishesDto;
import com.delitech.revealing.dto.RestaurantDto;
import com.delitech.revealing.entity.CategoryDishesEntity;
import com.delitech.revealing.entity.DishesEntity;
import com.delitech.revealing.entity.RestaurantEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.CategoryDishesRepository;
import com.delitech.revealing.repository.DishesRepository;
import com.delitech.revealing.repository.RestaurantRepository;
import com.delitech.revealing.service.DishesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_NOTFOUND;

@Service
@RequiredArgsConstructor
public class DishesServiceImp implements DishesService {

    public static final String NAME = "name";
    private final MessageSource messageSource;
    private final DishesRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryDishesRepository categoryDishesRepository;
    private final Function<DishesEntity, DishesDto> dishesToDto;
    private final Function<RestaurantEntity, RestaurantDto> restaurantToDto;
    private final Function<CategoryDishesEntity, CategoryDishesDto> categoryDishesToDto;
    private final Function<DishesDto, DishesEntity> dishesDtoToEntity;

    private DishesDto convert(DishesEntity entity) {
        return dishesToDto.apply(entity);
    }

    @Override
    @Transactional
    public DishesDto save(DishesDto dto, UUID userId, Locale locale) {
        RestaurantEntity restaurant = restaurantRepository.findById(userId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        CategoryDishesEntity categoryDishes = categoryDishesRepository.findById(dto.getCategoryDishes().getId())
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        DishesEntity entity = dishesDtoToEntity.apply(dto);

        entity.setRestaurant(restaurant);
        entity.setCategoryDishes(categoryDishes);

        return convert(repository.save(entity));
    }

    @Override
    @Transactional
    public DishesDto update(DishesDto dto, UUID id, Locale locale) {
        DishesEntity entity = repository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        BeanUtils.copyProperties(dto, entity, "id", "restaurant", "categoryDishes", "categoryDishesId");
        return convert(repository.save(entity));
    }

    @Override
    public DishesDto getById(UUID id, Locale locale) {
        DishesEntity entity = repository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        RestaurantEntity restaurant = restaurantRepository.findById(entity.getRestaurant().getUserId())
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        CategoryDishesEntity categoryDishes = categoryDishesRepository.findById(entity.getCategoryDishes().getId())
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        DishesDto dishesDto = convert(entity);

        dishesDto.setRestaurant(restaurantToDto.apply(restaurant));
        dishesDto.setCategoryDishes(categoryDishesToDto.apply(categoryDishes));
        return dishesDto;
    }

    @Override
    @Transactional
    public void delete(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(entity.getId());
    }

    @Override
    public Page<DishesDto> getAll(Pageable pageable, Locale locale) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    public List<DishesDto> getAllByRestaurant(UUID userId, Locale locale) {
        RestaurantEntity restaurant = restaurantRepository.findById(userId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));


        return repository.findAll(byRestaurant(restaurant), Sort.by(NAME)).stream().map(this::convert).toList();
    }

    @Override
    public Page<DishesDto> getAllByRestaurant(UUID userId, Pageable pageable, Locale locale) {
        RestaurantEntity restaurant = restaurantRepository.findById(userId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.findAll(byRestaurant(restaurant), pageable).map(this::convert);
    }

    @Override
    public List<DishesDto> getAllByRestaurantAndCategory(UUID userId, UUID categoryId, Locale locale) {
        RestaurantEntity restaurant = restaurantRepository.findById(userId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        CategoryDishesEntity category = categoryDishesRepository.findById(categoryId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.findAll(byRestaurant(restaurant).and(byCategory(category)), Sort.by(NAME)).stream().map(this::convert).toList();
    }

    @Override
    public Page<DishesDto> getAllByCategory(UUID categoryId, Pageable pageable, Locale locale) {
        CategoryDishesEntity category = categoryDishesRepository.findById(categoryId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.findAll(byCategory(category), pageable).map(this::convert);
    }

    private Specification<DishesEntity> byRestaurant(RestaurantEntity restaurant) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("restaurant"), restaurant);
    }

    private Specification<DishesEntity> byCategory(CategoryDishesEntity category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("categoryDishes"), category);
    }
}
