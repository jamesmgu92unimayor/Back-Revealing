package com.delitech.revealing.service.impl;

import com.delitech.revealing.commons.UserTypeEnum;
import com.delitech.revealing.dto.ClientDto;
import com.delitech.revealing.dto.UserDto;
import com.delitech.revealing.entity.ClientEntity;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.ClientRepository;
import com.delitech.revealing.repository.UserRepository;
import com.delitech.revealing.service.ClientCategoryRestaurantService;
import com.delitech.revealing.service.ClientDietaryRestrictionService;
import com.delitech.revealing.service.ClientKitchenTypeService;
import com.delitech.revealing.service.ClientService;
import com.delitech.revealing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_NOTFOUND;
import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_USER_INVALID;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements ClientService {

    private final MessageSource messageSource;
    private final ClientRepository repository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ClientCategoryRestaurantService clientCategoryRestaurantService;
    private final ClientDietaryRestrictionService clientDietaryRestrictionService;
    private final ClientKitchenTypeService clientKitchenTypeService;
    private final Function<UserEntity, UserDto> userToDto;
    private final Function<ClientDto, ClientEntity> clientDtoToEntity;
    private final Function<ClientEntity, ClientDto> clientToDto;


    @Override
    @Transactional
    public ClientDto save(ClientDto dto, Locale locale) {
        dto.getUser().setType(UserTypeEnum.CLIENT.toString());

        var clientEntity = clientDtoToEntity.apply(dto);
        var user = userService.save(dto.getUser(), locale);

        clientEntity.setUserId(user.getId());
        var client = clientToDto.apply(repository.save(clientEntity));

        client.setUser(user);
        return client;
    }

    @Override
    @Transactional
    public ClientDto update(ClientDto dto, UUID id, Locale locale) {
        ClientEntity client = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_USER_INVALID, null, locale)));

        userService.update(dto.getUser(), client.getUserId(), locale);
        BeanUtils.copyProperties(dto, client, "userId");
        return convert(repository.save(client));
    }

    @Override
    public ClientDto getById(UUID id, Locale locale) {
        var user = userService.getById(id, locale);

        var client = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_USER_INVALID, null, locale)));

        var clientDto = clientToDto.apply(client);

        clientDto.setUser(user);
        clientDto.setDietaryRestrictions(clientDietaryRestrictionService.getAllByClient(client.getUserId(), locale));
        clientDto.setKitchenTypes(clientKitchenTypeService.getAllByClient(client.getUserId(), locale));
        clientDto.setCategoryRestaurants(clientCategoryRestaurantService.getAllByClient(client.getUserId(), locale));
        return clientDto;
    }

    @Override
    public Page<ClientDto> getAll(Pageable pageable, Locale locale) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    public List<ClientDto> getAll(Locale locale) {
        return repository.findAll().stream().map(this::convert).toList();
    }

    @Override
    @Transactional
    public void delete(UUID id, Locale locale) {
        var entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        repository.deleteById(entity.getUserId());
        userService.delete(id, locale);
    }

    private ClientDto convert(ClientEntity entity) {
        var dto = clientToDto.apply(entity);
        var user = userRepository.findById(entity.getUserId()).orElse(null);

        dto.setUser(userToDto.apply(user));
        return dto;
    }
}
