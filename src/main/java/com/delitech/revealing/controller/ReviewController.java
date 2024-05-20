package com.delitech.revealing.controller;

import com.delitech.revealing.commons.GeneralBodyResponse;
import com.delitech.revealing.dto.ReviewDto;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

import static com.delitech.revealing.commons.Constants.*;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;
    private final MessageSource messageSource;

    @PostMapping("{restaurantId}")
    public ResponseEntity<GeneralBodyResponse<ReviewDto>> save(@Valid @RequestBody ReviewDto dto,
                                                               @AuthenticationPrincipal UserEntity loggedUser,
                                                               @PathVariable("restaurantId") UUID restaurantId,
                                                               Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_CREATE_SUCCESS, null, locale),
                        service.save(dto, loggedUser.getId(), restaurantId, locale)));
    }

    @PutMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<ReviewDto>> update(@PathVariable("id") UUID id,
                                                                 @Valid @RequestBody ReviewDto dto,
                                                                 @AuthenticationPrincipal UserEntity loggedUser,
                                                                 Locale locale) throws IllegalAccessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_UPDATE_SUCCESS, null, locale),
                        service.update(dto, id, loggedUser.getId(), locale)));
    }

    @GetMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<ReviewDto>> getById(@PathVariable("id") UUID id, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_SUCCESS, null, locale),
                        service.getById(id, locale)));
    }

    @GetMapping("my-reviews")
    public ResponseEntity<GeneralBodyResponse<Page<ReviewDto>>> getAllMyReviews(@AuthenticationPrincipal UserEntity loggedUser,
                                                                                Pageable pageable, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAllMyReviews(loggedUser.getId(), pageable, locale)));
    }

    @GetMapping("all/{restaurantId}")
    public ResponseEntity<GeneralBodyResponse<Page<ReviewDto>>> getAllByRestaurant(@PathVariable("restaurantId") UUID restaurantId,
                                                                                   Pageable pageable, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAllByRestaurant(restaurantId, pageable, locale)));
    }

    @GetMapping("my-reviews/{restaurantId}")
    public ResponseEntity<GeneralBodyResponse<Page<ReviewDto>>> getAllMyReviewsByRestaurant(@AuthenticationPrincipal UserEntity loggedUser,
                                                                                            @PathVariable("restaurantId") UUID restaurantId,
                                                                                            Pageable pageable, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.getAllMyReviewsByRestaurant(loggedUser.getId(), restaurantId, pageable, locale)));
    }

}
