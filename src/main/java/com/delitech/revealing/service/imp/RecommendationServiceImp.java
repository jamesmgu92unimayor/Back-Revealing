package com.delitech.revealing.service.imp;

import com.delitech.revealing.dto.*;
import com.delitech.revealing.entity.*;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.*;
import com.delitech.revealing.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_USER_INVALID;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImp implements RecommendationService {

    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ClientRepository clientRepository;
    private final ReviewRepository reviewRepository;
    private final DishesRepository dishesRepository;
    private final CategoryRestaurantRepository categoryRestaurantRepository;
    private final ClientCategoryRestaurantRepository clientCategoryRestaurantRepository;
    private final ClientKitchenTypeRepository clientKitchenTypeRepository;
    private final RestaurantKitchenTypeRepository restaurantKitchenTypeRepository;
    private final Function<KitchenTypeEntity, KitchenTypeDto> kitchenTypeToDto;
    private final Function<CategoryRestaurantEntity, CategoryRestaurantDto> categoryRestaurantToDto;

    @Override
    public List<RestaurantRecommenderDto> filterRestaurantByPreferencesClient(UUID clientId, Locale locale) {
        ClientEntity client = clientRepository.findById(clientId).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_USER_INVALID, null, locale)));
        List<ClientCategoryRestaurantEntity> categoriesByClient = clientCategoryRestaurantRepository.getByClient(client);
        List<ClientKitchenTypeEntity> kitchenTypeByClient = clientKitchenTypeRepository.getByClient(client);
        List<RestaurantEntity> restaurants = new ArrayList<>();

        categoriesByClient.forEach(clientCategoryRestaurantEntity ->
                restaurants.addAll(restaurantRepository.getByCategoryRestaurantId(clientCategoryRestaurantEntity.getCategoryRestaurant().getId())));

        kitchenTypeByClient.forEach(clientKitchenTypeEntity -> {
            List<RestaurantKitchenTypeEntity> byKitchenType = restaurantKitchenTypeRepository.getByKitchenType(clientKitchenTypeEntity.getKitchenType());

            byKitchenType.forEach(restaurantKitchenTypeEntity ->
                    restaurants.add(restaurantKitchenTypeEntity.getRestaurant()));
        });

        return restaurants.stream()
                .distinct()
                .sorted(Comparator.comparingDouble(this::calculateAverageScore).reversed())
                .map(this::convert).toList();
    }

    private RestaurantRecommenderDto convert(RestaurantEntity entity) {
        List<DishesEntity> dishes = dishesRepository.getByRestaurant(entity);
        List<RestaurantKitchenTypeEntity> restaurantKitchenTypes = restaurantKitchenTypeRepository.getByRestaurant(entity);
        UserEntity user = userRepository.findById(entity.getUserId()).orElse(UserEntity.builder().name("null").name("null").build());
        CategoryRestaurantEntity category = categoryRestaurantRepository.findById(entity.getCategoryRestaurantId()).orElse(null);
        List<KitchenTypeEntity> kitchenTypes = new ArrayList<>();

        restaurantKitchenTypes.forEach(restaurantKitchenTypeEntity -> kitchenTypes.add(restaurantKitchenTypeEntity.getKitchenType()));
        return RestaurantRecommenderDto.builder()
                .id(entity.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .address(entity.getAddress())
                .cellular(entity.getCellular())
                .categoryRestaurant(categoryRestaurantToDto.apply(category))
                .kitchenTypes(kitchenTypes.stream().map(kitchenTypeToDto).toList())
                .dishes(dishes.stream().map(this::convert).toList())
                .score(calculateAverageScore(entity))
                .build();
    }

    private DishesRecommenderDto convert(DishesEntity entity) {
        return DishesRecommenderDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .categoryDishes(CategoryDishesDto.builder()
                        .id(entity.getCategoryDishes().getId())
                        .name(entity.getCategoryDishes().getName())
                        .build())
                .build();
    }

    private double calculateAverageScore(RestaurantEntity restaurant) {
        List<ReviewEntity> reviews = reviewRepository.findByRestaurant(restaurant);
        return reviews.stream().mapToDouble(ReviewEntity::getScore).average().orElse(0);
    }
}
