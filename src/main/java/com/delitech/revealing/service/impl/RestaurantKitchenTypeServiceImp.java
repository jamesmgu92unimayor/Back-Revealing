package com.delitech.revealing.service.impl;

import com.delitech.revealing.dto.RestaurantKitchenTypeDto;
import com.delitech.revealing.entity.KitchenTypeEntity;
import com.delitech.revealing.entity.RestaurantEntity;
import com.delitech.revealing.entity.RestaurantKitchenTypeEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.KitchenTypeRepository;
import com.delitech.revealing.repository.RestaurantKitchenTypeRepository;
import com.delitech.revealing.repository.RestaurantRepository;
import com.delitech.revealing.service.RestaurantKitchenTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_NOTFOUND;

@Service
@RequiredArgsConstructor
public class RestaurantKitchenTypeServiceImp implements RestaurantKitchenTypeService {

    private final MessageSource messageSource;
    private final RestaurantKitchenTypeRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final KitchenTypeRepository kitchenTypeRepository;
    private final Function<RestaurantKitchenTypeEntity, RestaurantKitchenTypeDto> restaurantKitchenTypeToDto;

    private RestaurantKitchenTypeDto convert(RestaurantKitchenTypeEntity entity) {
        return restaurantKitchenTypeToDto.apply(entity);
    }

    @Override
    @Transactional
    public RestaurantKitchenTypeDto add(UUID userId, UUID typeId, Locale locale) {
        KitchenTypeEntity kitchenType = kitchenTypeRepository.findById(typeId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        RestaurantEntity restaurant = restaurantRepository.findById(userId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return convert(repository.save(RestaurantKitchenTypeEntity.builder().restaurant(restaurant).kitchenType(kitchenType).build()));
    }

    @Override
    @Transactional
    public void remove(UUID id, Locale locale) {
        RestaurantKitchenTypeEntity restaurantKitchenTypeEntity = repository.findById(id).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(restaurantKitchenTypeEntity.getId());
    }

    @Override
    public List<RestaurantKitchenTypeDto> getAllByRestaurant(UUID userId, Locale locale) {
        RestaurantEntity restaurant = restaurantRepository.findById(userId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.getByRestaurant(restaurant).stream().map(this::convert).toList();
    }
}
