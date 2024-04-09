package com.delitech.revealing.auth.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Objects;


public class CustomAuthTokenDto extends UsernamePasswordAuthenticationToken {
    private String type;

    public CustomAuthTokenDto(Object principal, Object credentials, String type) {
        super(principal, credentials);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomAuthTokenDto that = (CustomAuthTokenDto) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }
}
