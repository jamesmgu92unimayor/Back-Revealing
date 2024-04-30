package com.delitech.revealing.auth.util;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginRequest {
    String username;
    String password; 
}
