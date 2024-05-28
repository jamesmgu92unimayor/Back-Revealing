package com.delitech.revealing.service.impl;

import com.delitech.revealing.dto.ClientCategoryRestaurantDto;
import com.delitech.revealing.entity.CategoryRestaurantEntity;
import com.delitech.revealing.entity.ClientCategoryRestaurantEntity;
import com.delitech.revealing.entity.ClientEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.CategoryRestaurantRepository;
import com.delitech.revealing.repository.ClientCategoryRestaurantRepository;
import com.delitech.revealing.repository.ClientRepository;
import com.delitech.revealing.service.ClientCategoryRestaurantService;
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
public class ClientCategoryRestaurantServiceImp implements ClientCategoryRestaurantService {

    private final MessageSource messageSource;
    private final ClientCategoryRestaurantRepository repository;
    private final ClientRepository clientRepository;
    private final CategoryRestaurantRepository categoryRestaurantRepository;
    private final Function<ClientCategoryRestaurantEntity, ClientCategoryRestaurantDto> clientCategoryRestaurantToDto;

    private ClientCategoryRestaurantDto convert(ClientCategoryRestaurantEntity entity) {
        return clientCategoryRestaurantToDto.apply(entity);
    }

    @Override
    @Transactional
    public ClientCategoryRestaurantDto add(UUID userId, UUID categoryId, Locale locale) {
        CategoryRestaurantEntity categoryRestaurant = categoryRestaurantRepository.findById(categoryId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        ClientEntity client = clientRepository.findById(userId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return convert(repository.save(ClientCategoryRestaurantEntity.builder().client(client).categoryRestaurant(categoryRestaurant).build()));
    }

    @Override
    @Transactional
    public void remove(UUID id, Locale locale) {
        ClientCategoryRestaurantEntity clientCategoryRestaurantEntity = repository.findById(id).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(clientCategoryRestaurantEntity.getId());
    }

    @Override
    public List<ClientCategoryRestaurantDto> getAllByClient(UUID userId, Locale locale) {
        ClientEntity client = clientRepository.findById(userId).
                orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.getByClient(client).stream().map(this::convert).toList();
    }
}
