package com.delitech.revealing.controller;

import com.delitech.revealing.commons.GeneralBodyResponse;
import com.delitech.revealing.dto.DishesDto;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.service.DishesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.delitech.revealing.commons.Constants.*;

@RestController
@RequestMapping("dishes")
@RequiredArgsConstructor
public class DishesController {

    private final DishesService service;
    private final MessageSource messageSource;

    @PostMapping()
    public ResponseEntity<GeneralBodyResponse<DishesDto>> save(@Valid @RequestBody DishesDto dto, @AuthenticationPrincipal UserEntity loggedUser, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_CREATE_SUCCESS, null, locale),
                        service.save(dto, loggedUser.getId(), locale)));
    }

    @PutMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<DishesDto>> update(@PathVariable("id") UUID id, @Valid @RequestBody DishesDto dto, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_UPDATE_SUCCESS, null, locale),
                        service.update(dto, id, locale)));
    }

    @GetMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<DishesDto>> getById(@PathVariable("id") UUID id, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_SUCCESS, null, locale),
                        service.getById(id, locale)));
    }

    @GetMapping("page")
    public ResponseEntity<GeneralBodyResponse<Page<DishesDto>>> getPage(Pageable pageable, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAll(pageable, locale)));
    }

    @GetMapping("all/{restaurantId}")
    public ResponseEntity<GeneralBodyResponse<List<DishesDto>>> getAllByRestaurant(@PathVariable("restaurantId") UUID restaurantId, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAllByRestaurant(restaurantId, locale)));
    }

    @GetMapping("page/{restaurantId}")
    public ResponseEntity<GeneralBodyResponse<Page<DishesDto>>> getAllByRestaurant(@PathVariable("restaurantId") UUID restaurantId, Pageable pageable, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAllByRestaurant(restaurantId, pageable, locale)));
    }

    @GetMapping("all/{restaurantId}/{categoryId}")
    public ResponseEntity<GeneralBodyResponse<List<DishesDto>>> getAllByRestaurantAndCategory(@PathVariable("restaurantId") UUID restaurantId,
                                                                                              @PathVariable("categoryId") UUID categoryId,
                                                                                              Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAllByRestaurantAndCategory(restaurantId, categoryId, locale)));
    }

    @GetMapping("page/{categoryId}")
    public ResponseEntity<GeneralBodyResponse<Page<DishesDto>>> getAllByCategory(@PathVariable("categoryId") UUID categoryId, Pageable pageable, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAllByCategory(categoryId, pageable, locale)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<Void>> delete(@PathVariable("id") UUID id, Locale locale) {
        service.delete(id, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_DELETE_SUCCESS, null, locale), null));
    }
}
