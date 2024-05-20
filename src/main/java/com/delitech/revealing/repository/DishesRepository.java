package com.delitech.revealing.repository;

import com.delitech.revealing.entity.DishesEntity;
import com.delitech.revealing.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DishesRepository extends JpaRepository<DishesEntity, UUID>, JpaSpecificationExecutor<DishesEntity> {

    List<DishesEntity> getByRestaurant(RestaurantEntity restaurant);
}
