package com.delitech.revealing.mapper;

import com.delitech.revealing.dto.UserDto;
import com.delitech.revealing.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ConverterUserFactory {

    @Bean
    public Function<UserEntity, UserDto> userToDto(ModelMapper mapper) {
        return userEntity -> mapper.map(userEntity, UserDto.class);
    }

    @Bean
    public Function<UserDto, UserEntity> userDtoToEntity(ModelMapper mapper) {
        return userDto -> mapper.map(userDto, UserEntity.class);
    }
}
