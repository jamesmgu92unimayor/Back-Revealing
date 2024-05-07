package com.delitech.revealing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto implements Serializable {
    private String name;
    private String lastname;
    private String cellular;
    private UserDto user;
}
