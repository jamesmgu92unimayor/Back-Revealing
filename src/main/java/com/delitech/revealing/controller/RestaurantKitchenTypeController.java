package com.delitech.revealing.controller;

import com.delitech.revealing.commons.GeneralBodyResponse;
import com.delitech.revealing.dto.RestaurantKitchenTypeDto;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.service.RestaurantKitchenTypeService;
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
@RequestMapping("restaurant")
@RequiredArgsConstructor
public class RestaurantKitchenTypeController {

    private final RestaurantKitchenTypeService service;
    private final MessageSource messageSource;

    @PostMapping("kitchen-type/{typeId}")
    public ResponseEntity<GeneralBodyResponse<RestaurantKitchenTypeDto>> save(@AuthenticationPrincipal UserEntity loggedUser, @PathVariable("typeId") UUID typeId, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_CREATE_SUCCESS, null, locale),
                        service.add(loggedUser.getId(), typeId, locale)));
    }

    @GetMapping("kitchen-type")
    public ResponseEntity<GeneralBodyResponse<List<RestaurantKitchenTypeDto>>> getAll(@AuthenticationPrincipal UserEntity loggedUser, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAllByRestaurant(loggedUser.getId(), locale)));
    }

    @DeleteMapping("kitchen-type/{typeId}")
    public ResponseEntity<GeneralBodyResponse<Void>> delete(@PathVariable("typeId") UUID id, Locale locale) {
        service.remove(id, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_DELETE_SUCCESS, null, locale), null));
    }
}
