package com.delitech.revealing.controller;

import com.delitech.revealing.commons.GeneralBodyResponse;
import com.delitech.revealing.dto.UserDto;
import com.delitech.revealing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

import static com.delitech.revealing.commons.Constants.GENERAL_CREATE_SUCCESS;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final MessageSource messageSource;
    private final UserService service;

    @PostMapping
    public ResponseEntity<GeneralBodyResponse<UserDto>> save(@Valid @RequestBody UserDto userDto, Locale locale) {
        UserDto user = service.save(userDto, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_CREATE_SUCCESS, null, locale), user));
    }

}
