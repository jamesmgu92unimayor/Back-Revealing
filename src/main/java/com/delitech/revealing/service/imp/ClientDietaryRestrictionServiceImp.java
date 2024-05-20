package com.delitech.revealing.service.imp;

import com.delitech.revealing.dto.ClientDietaryRestrictionDto;
import com.delitech.revealing.entity.ClientDietaryRestrictionEntity;
import com.delitech.revealing.entity.ClientEntity;
import com.delitech.revealing.entity.DietaryRestrictionEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.ClientDietaryRestrictionRepository;
import com.delitech.revealing.repository.ClientRepository;
import com.delitech.revealing.repository.DietaryRestrictionRepository;
import com.delitech.revealing.service.ClientDietaryRestrictionService;
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
public class ClientDietaryRestrictionServiceImp implements ClientDietaryRestrictionService {

    private final MessageSource messageSource;
    private final ClientDietaryRestrictionRepository repository;
    private final ClientRepository clientRepository;
    private final DietaryRestrictionRepository dietaryRestrictionRepository;
    private final Function<ClientDietaryRestrictionEntity, ClientDietaryRestrictionDto> clientKitchenTypeToDto;

    private ClientDietaryRestrictionDto convert(ClientDietaryRestrictionEntity entity) {
        return clientKitchenTypeToDto.apply(entity);
    }

    @Override
    @Transactional
    public ClientDietaryRestrictionDto add(UUID userId, UUID typeId, Locale locale) {
        DietaryRestrictionEntity dietaryRestriction = dietaryRestrictionRepository.findById(typeId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        ClientEntity client = clientRepository.findById(userId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return convert(repository.save(ClientDietaryRestrictionEntity.builder().client(client).dietaryRestriction(dietaryRestriction).build()));
    }

    @Override
    @Transactional
    public void remove(UUID id, Locale locale) {
        ClientDietaryRestrictionEntity clientDietaryRestrictionEntity = repository.findById(id).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(clientDietaryRestrictionEntity.getId());
    }

    @Override
    public List<ClientDietaryRestrictionDto> getAllByClient(UUID userId, Locale locale) {
        ClientEntity client = clientRepository.findById(userId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.getByClient(client).stream().map(this::convert).toList();
    }
}
