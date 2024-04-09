package com.delitech.revealing.auth.util;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginRequest {
    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String type;
}
