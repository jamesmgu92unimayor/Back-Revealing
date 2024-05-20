package com.delitech.revealing.controller;

import com.delitech.revealing.commons.GeneralBodyResponse;
import com.delitech.revealing.dto.ClientCategoryRestaurantDto;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.service.ClientCategoryRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.delitech.revealing.commons.Constants.*;

@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class ClientCategoryRestaurantController {

    private final ClientCategoryRestaurantService service;
    private final MessageSource messageSource;

    @PostMapping("category-restaurant/{categoryId}")
    public ResponseEntity<GeneralBodyResponse<ClientCategoryRestaurantDto>> save(@AuthenticationPrincipal UserEntity loggedUser, @PathVariable("categoryId") UUID categoryId, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_CREATE_SUCCESS, null, locale),
                        service.add(loggedUser.getId(), categoryId, locale)));
    }

    @GetMapping("category-restaurant")
    public ResponseEntity<GeneralBodyResponse<List<ClientCategoryRestaurantDto>>> getAll(@AuthenticationPrincipal UserEntity loggedUser, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAllByClient(loggedUser.getId(), locale)));
    }

    @DeleteMapping("category-restaurant/{categoryId}")
    public ResponseEntity<GeneralBodyResponse<Void>> delete(@PathVariable("categoryId") UUID id, Locale locale) {
        service.remove(id, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_DELETE_SUCCESS, null, locale), null));
    }
}
