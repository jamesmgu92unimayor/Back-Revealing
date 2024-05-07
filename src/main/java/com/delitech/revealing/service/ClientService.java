package com.delitech.revealing.service;


import com.delitech.revealing.dto.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface ClientService {

    ClientDto save(ClientDto dto, Locale locale);

    ClientDto update(ClientDto dto, UUID id, Locale locale);

    ClientDto getById(UUID id, Locale locale);

    Page<ClientDto> getAll(Pageable pageable, Locale locale);

    List<ClientDto> getAll(Locale locale);

    void delete(UUID id, Locale locale);
}
