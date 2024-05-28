package com.delitech.revealing.service.impl;

import com.delitech.revealing.commons.UserTypeEnum;
import com.delitech.revealing.dto.UserDto;
import com.delitech.revealing.entity.AdminEntity;
import com.delitech.revealing.entity.ClientEntity;
import com.delitech.revealing.entity.RestaurantEntity;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.exception.BusinessLogicException;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.AdminRepository;
import com.delitech.revealing.repository.ClientRepository;
import com.delitech.revealing.repository.RestaurantRepository;
import com.delitech.revealing.repository.UserRepository;
import com.delitech.revealing.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_EMAIL;
import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_USER_INVALID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final MessageSource messageSource;
    private final UserRepository repository;
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final RestaurantRepository restaurantRepository;
    private final Function<UserDto, UserEntity> userDtoToEntity;
    private final Function<UserEntity, UserDto> userToDto;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto save(UserDto userDto, Locale locale) {
        this.checkIfExistEmail(userDto.getEmail(), locale);

        UserEntity userEntity = this.userDtoToEntity.apply(userDto);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userEntity = repository.save(userEntity);

        if(UserTypeEnum.ADMIN.toString().equals(userDto.getType()))
            adminRepository.save(AdminEntity.builder().userId(userEntity.getId()).build());
        else if(UserTypeEnum.CLIENT.toString().equals(userDto.getType()))
            clientRepository.save(ClientEntity.builder().userId(userEntity.getId()).build());
        else if(UserTypeEnum.RESTAURANT.toString().equals(userDto.getType()))
            restaurantRepository.save(RestaurantEntity.builder().userId(userEntity.getId()).build());

        return userToDto.apply(userEntity);
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto, UUID id, Locale locale) {
        UserEntity user = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_USER_INVALID, null, locale)));

        if (repository.existsByEmailAndUser(userDto.getEmail(), id)) {
            throw new BusinessLogicException(messageSource.getMessage(EXCEPTION_MODEL_EMAIL, null, locale));
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user = repository.save(user);
        return userToDto.apply(user);
    }

    @Override
    public UserDto getById(UUID id, Locale locale) {
        UserEntity user = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_USER_INVALID, null, locale)));

        return userToDto.apply(user);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable, Locale locale) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    @Transactional
    public void delete(UUID id, Locale locale) {
        UserEntity user = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_USER_INVALID, null, locale)));

        repository.deleteById(user.getId());
    }

    private UserDto convert(UserEntity entity) {
        return userToDto.apply(entity);
    }


    /**
     * Funcion para validar correo
     *
     * @param email
     * @param locale
     */
    private void checkIfExistEmail(String email, Locale locale) {
        if (repository.existsByEmail(email)) {
            throw new BusinessLogicException(
                    messageSource.getMessage(EXCEPTION_MODEL_EMAIL, null, locale)
            );
        }
    }

}
