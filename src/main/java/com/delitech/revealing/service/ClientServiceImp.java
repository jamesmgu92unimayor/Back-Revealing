package com.delitech.revealing.service;

import com.delitech.revealing.dto.ClientDto;
import com.delitech.revealing.dto.UserDto;
import com.delitech.revealing.entity.ClientEntity;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.ClientRepository;
import com.delitech.revealing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    private final Function<UserDto, UserEntity> userDtoToEntity;
    private final Function<UserEntity, UserDto> userToDto;
    private final Function<ClientDto, ClientEntity> clientDtoToEntity;
    private final Function<ClientEntity, ClientDto> clientToDto;


    @Override
    @Transactional
    public ClientDto save(ClientDto dto, Locale locale) {
        var apply = clientDtoToEntity.apply(dto);
        var user = userService.save(dto.getUser(), locale);

        apply.setUserId(user.getId());
        var client = clientToDto.apply(repository.save(apply));

        client.setUser(user);
        return client;
    }

    @Override
    public ClientDto update(ClientDto dto, UUID id, Locale locale) {

        return null;
    }

    @Override
    public ClientDto getById(UUID id, Locale locale) {
        var user = userService.getById(id, locale);

        var client = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_USER_INVALID, null, locale)));

        var apply = clientToDto.apply(client);

        apply.setUser(user);
        return apply;
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
