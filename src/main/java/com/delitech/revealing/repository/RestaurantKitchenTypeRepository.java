package com.delitech.revealing.repository;

import com.delitech.revealing.entity.RestaurantEntity;
import com.delitech.revealing.entity.RestaurantKitchenTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantKitchenTypeRepository extends JpaRepository<RestaurantKitchenTypeEntity, UUID>, JpaSpecificationExecutor<RestaurantKitchenTypeEntity> {

    List<RestaurantKitchenTypeEntity> getByRestaurant(RestaurantEntity restaurant);
}
