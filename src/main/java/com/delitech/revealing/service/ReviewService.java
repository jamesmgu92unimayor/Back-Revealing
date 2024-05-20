package com.delitech.revealing.service;


import com.delitech.revealing.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Locale;
import java.util.UUID;

public interface ReviewService {

    ReviewDto save(ReviewDto dto, UUID clientId, UUID restaurantId, Locale locale);

    ReviewDto update(ReviewDto dto, UUID id, UUID clientId, Locale locale) throws IllegalAccessException;

    ReviewDto getById(UUID id, Locale locale);

    Page<ReviewDto> getAllMyReviews(UUID clientId, Pageable pageable, Locale locale);

    Page<ReviewDto> getAllByRestaurant(UUID restaurantId, Pageable pageable, Locale locale);

    Page<ReviewDto> getAllMyReviewsByRestaurant(UUID clientId, UUID restaurantId, Pageable pageable, Locale locale);

}
