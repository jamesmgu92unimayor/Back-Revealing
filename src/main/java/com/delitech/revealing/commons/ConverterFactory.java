package com.delitech.revealing.commons;

import com.delitech.revealing.dto.*;
import com.delitech.revealing.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ConverterFactory {

    @Bean
    public Function<UserEntity, UserDto> userToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, UserDto.class);
    }

    @Bean
    public Function<UserDto, UserEntity> userDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, UserEntity.class);
    }

    @Bean
    public Function<AdminEntity, AdminDto> adminToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, AdminDto.class);
    }

    @Bean
    public Function<AdminDto, AdminEntity> adminDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, AdminEntity.class);
    }

    @Bean
    public Function<CategoryDishesEntity, CategoryDishesDto> categoryDishesToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, CategoryDishesDto.class);
    }

    @Bean
    public Function<CategoryDishesDto, CategoryDishesEntity> categoryDishesDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, CategoryDishesEntity.class);
    }

    @Bean
    public Function<CategoryRestaurantEntity, CategoryRestaurantDto> categoryRestaurantToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, CategoryRestaurantDto.class);
    }

    @Bean
    public Function<CategoryRestaurantDto, CategoryRestaurantEntity> categoryRestaurantDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, CategoryRestaurantEntity.class);
    }

    @Bean
    public Function<ClientCategoryRestaurantEntity, ClientCategoryRestaurantDto> clientCategoryRestaurantToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, ClientCategoryRestaurantDto.class);
    }

    @Bean
    public Function<ClientCategoryRestaurantDto, ClientCategoryRestaurantEntity> clientCategoryRestaurantDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, ClientCategoryRestaurantEntity.class);
    }

    @Bean
    public Function<ClientDietaryRestrictionEntity, ClientDietaryRestrictionDto> clientDietaryRestrictionToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, ClientDietaryRestrictionDto.class);
    }

    @Bean
    public Function<ClientDietaryRestrictionDto, ClientDietaryRestrictionEntity> clientDietaryRestrictionDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, ClientDietaryRestrictionEntity.class);
    }

    @Bean
    public Function<ClientEntity, ClientDto> clientToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, ClientDto.class);
    }

    @Bean
    public Function<ClientDto, ClientEntity> clientDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, ClientEntity.class);
    }

    @Bean
    public Function<ClientKitchenTypeEntity, ClientKitchenTypeDto> clientKitchenTypeToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, ClientKitchenTypeDto.class);
    }

    @Bean
    public Function<ClientKitchenTypeDto, ClientKitchenTypeEntity> clientKitchenTypeDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, ClientKitchenTypeEntity.class);
    }

    @Bean
    public Function<DietaryRestrictionEntity, DietaryRestrictionDto> dietaryRestrictionToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, DietaryRestrictionDto.class);
    }

    @Bean
    public Function<DietaryRestrictionDto, DietaryRestrictionEntity> dietaryRestrictionDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, DietaryRestrictionEntity.class);
    }

    @Bean
    public Function<DishesEntity, DishesDto> dishesToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, DishesDto.class);
    }

    @Bean
    public Function<DishesDto, DishesEntity> dishesDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, DishesEntity.class);
    }

    @Bean
    public Function<KitchenTypeEntity, KitchenTypeDto> kitchenTypeToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, KitchenTypeDto.class);
    }

    @Bean
    public Function<KitchenTypeDto, KitchenTypeEntity> kitchenTypeDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, KitchenTypeEntity.class);
    }

    @Bean
    public Function<RestaurantEntity, RestaurantDto> restaurantToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, RestaurantDto.class);
    }

    @Bean
    public Function<RestaurantDto, RestaurantEntity> restaurantDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, RestaurantEntity.class);
    }

    @Bean
    public Function<RestaurantKitchenTypeEntity, RestaurantKitchenTypeDto> restaurantKitchenTypeToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, RestaurantKitchenTypeDto.class);
    }

    @Bean
    public Function<RestaurantKitchenTypeDto, RestaurantKitchenTypeEntity> restaurantKitchenTypeDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, RestaurantKitchenTypeEntity.class);
    }

    @Bean
    public Function<ReviewEntity, ReviewDto> reviewToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, ReviewDto.class);
    }

    @Bean
    public Function<ReviewDto, ReviewEntity> reviewDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, ReviewEntity.class);
    }
}
