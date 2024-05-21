package com.delitech.revealing.controller;

import com.delitech.revealing.commons.GeneralBodyResponse;
import com.delitech.revealing.dto.RestaurantRecommenderDto;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

import static com.delitech.revealing.commons.Constants.GENERAL_LIST_SUCCESS;

@RestController
@RequestMapping("recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final MessageSource messageSource;
    private final RecommendationService service;


    @GetMapping("all")
    public ResponseEntity<GeneralBodyResponse<List<RestaurantRecommenderDto>>> filterRestaurantByPreferencesClient(@AuthenticationPrincipal UserEntity loggedUser, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(messageSource.getMessage(GENERAL_LIST_SUCCESS, null, locale),
                        service.filterRestaurantByPreferencesClient(loggedUser.getId(), locale)));
    }
}
