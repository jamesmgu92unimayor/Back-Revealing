package com.delitech.revealing.controller;

import com.delitech.revealing.commons.GeneralBodyResponse;
import com.delitech.revealing.dto.CategoryRestaurantDto;
import com.delitech.revealing.service.CategoryRestaurantService;
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
@RequestMapping("category-restaurant")
@RequiredArgsConstructor
public class CategoryRestaurantController {

    private final CategoryRestaurantService service;
    private final MessageSource messageSource;

    @PostMapping()
    public ResponseEntity<GeneralBodyResponse<CategoryRestaurantDto>> save(@Valid @RequestBody CategoryRestaurantDto dto, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_CREATE_SUCCESS, null, locale),
                        service.save(dto, locale)));
    }

    @PutMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<CategoryRestaurantDto>> update(@PathVariable("id") UUID id, @Valid @RequestBody CategoryRestaurantDto dto, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_UPDATE_SUCCESS, null, locale),
                        service.update(dto, id, locale)));
    }

    @GetMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<CategoryRestaurantDto>> getById(@PathVariable("id") UUID id, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_SUCCESS, null, locale),
                        service.getById(id, locale)));
    }

    @GetMapping("page")
    public ResponseEntity<GeneralBodyResponse<Page<CategoryRestaurantDto>>> getPage(Pageable pageable, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAll(pageable, locale)));
    }

    @GetMapping("all")
    public ResponseEntity<GeneralBodyResponse<List<CategoryRestaurantDto>>> getAll(Locale locale) {
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
