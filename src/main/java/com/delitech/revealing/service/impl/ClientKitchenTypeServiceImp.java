package com.delitech.revealing.service.impl;

import com.delitech.revealing.dto.ClientKitchenTypeDto;
import com.delitech.revealing.entity.ClientEntity;
import com.delitech.revealing.entity.ClientKitchenTypeEntity;
import com.delitech.revealing.entity.KitchenTypeEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.ClientKitchenTypeRepository;
import com.delitech.revealing.repository.ClientRepository;
import com.delitech.revealing.repository.KitchenTypeRepository;
import com.delitech.revealing.service.ClientKitchenTypeService;
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
public class ClientKitchenTypeServiceImp implements ClientKitchenTypeService {

    private final MessageSource messageSource;
    private final ClientKitchenTypeRepository repository;
    private final ClientRepository clientRepository;
    private final KitchenTypeRepository kitchenTypeRepository;
    private final Function<ClientKitchenTypeEntity, ClientKitchenTypeDto> clientKitchenTypeToDto;

    private ClientKitchenTypeDto convert(ClientKitchenTypeEntity entity) {
        return clientKitchenTypeToDto.apply(entity);
    }

    @Override
    @Transactional
    public ClientKitchenTypeDto add(UUID userId, UUID typeId, Locale locale) {
        KitchenTypeEntity kitchenType = kitchenTypeRepository.findById(typeId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        ClientEntity client = clientRepository.findById(userId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return convert(repository.save(ClientKitchenTypeEntity.builder().client(client).kitchenType(kitchenType).build()));
    }

    @Override
    @Transactional
    public void remove(UUID id, Locale locale) {
        ClientKitchenTypeEntity clientKitchenTypeEntity = repository.findById(id).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(clientKitchenTypeEntity.getId());
    }

    @Override
    public List<ClientKitchenTypeDto> getAllByClient(UUID userId, Locale locale) {
        ClientEntity client = clientRepository.findById(userId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.getByClient(client).stream().map(this::convert).toList();
    }
}
