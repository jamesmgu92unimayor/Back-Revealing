package com.delitech.revealing.service;


import com.delitech.revealing.dto.ClientKitchenTypeDto;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface ClientKitchenTypeService {

    ClientKitchenTypeDto add(UUID userId, UUID typeId, Locale locale);

    void remove(UUID id, Locale locale);

    List<ClientKitchenTypeDto> getAllByClient(UUID userId, Locale locale);

}
