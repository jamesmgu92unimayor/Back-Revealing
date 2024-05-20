package com.delitech.revealing.controller;

import com.delitech.revealing.commons.GeneralBodyResponse;
import com.delitech.revealing.dto.RestaurantDto;
import com.delitech.revealing.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.delitech.revealing.commons.Constants.*;

@RestController
@RequestMapping("restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final MessageSource messageSource;
    private final RestaurantService service;

    @PostMapping
    public ResponseEntity<GeneralBodyResponse<RestaurantDto>> save(@Valid @RequestBody RestaurantDto dto, Locale locale) {
        var client = service.save(dto, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_CREATE_SUCCESS, null, locale), client));
    }

    @PutMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<RestaurantDto>> update(@Valid @RequestBody RestaurantDto dto, @PathVariable("id") UUID id, Locale locale) {
        var client = service.update(dto, id, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_CREATE_SUCCESS, null, locale), client));
    }

    @GetMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<RestaurantDto>> getById(@PathVariable("id") UUID id, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_SUCCESS, null, locale),
                        service.getById(id, locale)));
    }

    @GetMapping("page")
    public ResponseEntity<GeneralBodyResponse<Page<RestaurantDto>>> getPage(Pageable pageable, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAll(pageable, locale)));
    }

    @GetMapping("all")
    public ResponseEntity<GeneralBodyResponse<List<RestaurantDto>>> getAll(Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAll(locale)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<Void>> delete(@PathVariable("id") UUID id, Locale locale) {
        service.delete(id, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_DELETE_SUCCESS, null, locale), null));
    }
}
