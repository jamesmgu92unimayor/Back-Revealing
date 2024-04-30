package com.delitech.revealing.repository;

import com.delitech.revealing.entity.ClientCategoryRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientCategoryRestaurantRepository extends JpaRepository<ClientCategoryRestaurantEntity, UUID>, JpaSpecificationExecutor<ClientCategoryRestaurantEntity> {

}
