package com.delitech.revealing.repository;

import com.delitech.revealing.entity.CategoryRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRestaurantRepository extends JpaRepository<CategoryRestaurantEntity, UUID>, JpaSpecificationExecutor<CategoryRestaurantEntity> {

}
