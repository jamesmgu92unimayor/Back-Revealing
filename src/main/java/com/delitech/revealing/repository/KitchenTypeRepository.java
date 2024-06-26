package com.delitech.revealing.repository;

import com.delitech.revealing.entity.KitchenTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KitchenTypeRepository extends JpaRepository<KitchenTypeEntity, UUID>, JpaSpecificationExecutor<KitchenTypeEntity> {

}
