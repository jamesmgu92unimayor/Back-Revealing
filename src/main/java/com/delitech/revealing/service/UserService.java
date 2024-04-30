package com.delitech.revealing.service;


import com.delitech.revealing.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Locale;
import java.util.UUID;

public interface UserService {

    UserDto save(UserDto userDto, Locale locale);

    UserDto update(UserDto userDto, UUID id, Locale locale);

    UserDto getById(UUID id, Locale locale);

    Page<UserDto> getAll(Pageable pageable, Locale locale);

    void delete(UUID id, Locale locale);
}
