package com.delitech.revealing.repository;

import com.delitech.revealing.entity.ClientDietaryRestrictionEntity;
import com.delitech.revealing.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientDietaryRestrictionRepository extends JpaRepository<ClientDietaryRestrictionEntity, UUID>, JpaSpecificationExecutor<ClientDietaryRestrictionEntity> {

    List<ClientDietaryRestrictionEntity> getByClient(ClientEntity client);
}
