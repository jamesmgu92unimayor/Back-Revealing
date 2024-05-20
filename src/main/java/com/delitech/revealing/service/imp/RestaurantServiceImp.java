package com.delitech.revealing.service.imp;

import com.delitech.revealing.dto.CategoryRestaurantDto;
import com.delitech.revealing.dto.RestaurantDto;
import com.delitech.revealing.dto.UserDto;
import com.delitech.revealing.entity.CategoryRestaurantEntity;
import com.delitech.revealing.entity.RestaurantEntity;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.CategoryRestaurantRepository;
import com.delitech.revealing.repository.RestaurantRepository;
import com.delitech.revealing.repository.UserRepository;
import com.delitech.revealing.service.RestaurantKitchenTypeService;
import com.delitech.revealing.service.RestaurantService;
import com.delitech.revealing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_NOTFOUND;
import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_USER_INVALID;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImp implements RestaurantService {

    private final MessageSource messageSource;
    private final RestaurantRepository repository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CategoryRestaurantRepository restaurantRepository;
    private final RestaurantKitchenTypeService kitchenTypeService;
    private final Function<UserEntity, UserDto> userToDto;
    private final Function<RestaurantDto, RestaurantEntity> restaurantDtoToEntity;
    private final Function<RestaurantEntity, RestaurantDto> restaurantToDto;
    private final Function<CategoryRestaurantEntity, CategoryRestaurantDto> categoryRestaurantToDto;


    @Override
    @Transactional
    public RestaurantDto save(RestaurantDto dto, Locale locale) {
        var restaurantEntity = restaurantDtoToEntity.apply(dto);
        var user = userService.save(dto.getUser(), locale);
        CategoryRestaurantEntity categoryRestaurant = restaurantRepository.findById(dto.getCategoryRestaurantId()).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        restaurantEntity.setUserId(user.getId());
        restaurantEntity.setCategoryRestaurant(categoryRestaurant);
        var restaurant = restaurantToDto.apply(repository.save(restaurantEntity));

        restaurant.setUser(user);
        restaurant.setCategoryRestaurant(categoryRestaurantToDto.apply(categoryRestaurant));
        return restaurant;
    }

    @Override
    @Transactional
    public RestaurantDto update(RestaurantDto dto, UUID id, Locale locale) {
        RestaurantEntity restaurant = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_USER_INVALID, null, locale)));

        userService.update(dto.getUser(), restaurant.getUserId(), locale);
        BeanUtils.copyProperties(dto, restaurant, "categoryRestaurantId", "userId", "categoryRestaurant");
        return convert(repository.save(restaurant));
    }

    @Override
    public RestaurantDto getById(UUID id, Locale locale) {
        var user = userService.getById(id, locale);
        var restaurant = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_USER_INVALID, null, locale)));
        CategoryRestaurantEntity categoryRestaurant = restaurantRepository.findById(restaurant.getCategoryRestaurantId()).orElseThrow(() ->
                new ModelNotFoundException("Parametro no encontrado"));
        var restaurantDto = restaurantToDto.apply(restaurant);

        restaurantDto.setUser(user);
        restaurantDto.setCategoryRestaurant(categoryRestaurantToDto.apply(categoryRestaurant));
        restaurantDto.setKitchenTypes(kitchenTypeService.getAllByRestaurant(restaurant.getUserId(), locale));
        return restaurantDto;
    }

    @Override
    public Page<RestaurantDto> getAll(Pageable pageable, Locale locale) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    public List<RestaurantDto> getAll(Locale locale) {
        return repository.findAll().stream().map(this::convert).toList();
    }

    @Override
    @Transactional
    public void delete(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(entity.getUserId());
        userService.delete(id, locale);
    }

    private RestaurantDto convert(RestaurantEntity entity) {
        var dto = restaurantToDto.apply(entity);
        var user = userRepository.findById(entity.getUserId()).orElse(null);
        CategoryRestaurantEntity categoryRestaurant = restaurantRepository.findById(entity.getCategoryRestaurantId()).orElseThrow(() ->
                new ModelNotFoundException("Parametro no encontrado"));


        dto.setUser(userToDto.apply(user));
        dto.setCategoryRestaurant(categoryRestaurantToDto.apply(categoryRestaurant));
        return dto;
    }
}
