package com.delitech.revealing.service.Impl;

import com.delitech.revealing.dto.KitchenTypeDto;
import com.delitech.revealing.entity.KitchenTypeEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.KitchenTypeRepository;
import com.delitech.revealing.service.impl.KitchenTypeServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KitchenTypeServiceTest {
    private MessageSource messageSource;
    private KitchenTypeRepository repository;
    private Function<KitchenTypeEntity, KitchenTypeDto> kitchenTypeToDto;
    private Function<KitchenTypeDto, KitchenTypeEntity> kitchenTypeDtoToEntity;
    private KitchenTypeServiceImp target;

    @BeforeEach
    void setUp() {
        messageSource = mock(MessageSource.class);
        repository = mock(KitchenTypeRepository.class);
        kitchenTypeToDto = kitchenType -> {
            ModelMapper objectMapper = new ModelMapper();

            return objectMapper.map(kitchenType, KitchenTypeDto.class);
        };
        kitchenTypeDtoToEntity = kitchenTypeDto -> {
            ModelMapper objectMapper = new ModelMapper();

            return objectMapper.map(kitchenTypeDto, KitchenTypeEntity.class);
        };
        target = new KitchenTypeServiceImp(messageSource, repository, kitchenTypeToDto, kitchenTypeDtoToEntity);
    }

    @Test
    void save_thenOk() {
        KitchenTypeEntity entity = getKitchenTypeEntity();

        when(repository.save(any())).thenReturn(entity);

        KitchenTypeDto kitchenTypeDto = target.save(getKitchenTypeDto(), Locale.getDefault());

        assertNotNull(kitchenTypeDto);
        verify(repository, times(1)).save(any());
    }

    @Test
    void update_thenOk() {
        KitchenTypeEntity entity = getKitchenTypeEntity();

        when(repository.save(any())).thenReturn(entity);
        when(repository.findById(any())).thenReturn(Optional.of(entity));

        KitchenTypeDto kitchenTypeDto = target.update(getKitchenTypeDto(), UUID.randomUUID(), Locale.getDefault());

        assertNotNull(kitchenTypeDto);
        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    void update_whenNotExist_thenThrow() {
        KitchenTypeDto unitDto = getKitchenTypeDto();

        when(repository.findById(any())).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("message");

        ModelNotFoundException exception = assertThrows(ModelNotFoundException.class, () -> target.update(unitDto, UUID.randomUUID(), Locale.getDefault()));

        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        verify(repository, times(1)).findById(any());
        verify(repository, never()).save(any());
    }

    private KitchenTypeEntity getKitchenTypeEntity() {
        return KitchenTypeEntity.builder()
                .id(UUID.randomUUID())
                .name("name")
                .build();
    }

    private KitchenTypeDto getKitchenTypeDto() {
        return KitchenTypeDto.builder()
                .id(UUID.randomUUID())
                .name("name")
                .build();
    }

}
