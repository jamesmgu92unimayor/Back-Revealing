package com.delitech.revealing.service;


import com.delitech.revealing.dto.RestaurantRecommenderDto;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface RecommendationService {

    List<RestaurantRecommenderDto> filterRestaurantByPreferencesClient(UUID clientId, Locale locale);
}
