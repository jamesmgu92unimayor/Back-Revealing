package com.delitech.revealing.service;


import com.delitech.revealing.dto.ClientDietaryRestrictionDto;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface ClientDietaryRestrictionService {

    ClientDietaryRestrictionDto add(UUID userId, UUID restrictionId, Locale locale);

    void remove(UUID id, Locale locale);

    List<ClientDietaryRestrictionDto> getAllByClient(UUID userId, Locale locale);

}
