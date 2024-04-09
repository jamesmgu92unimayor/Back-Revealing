package com.delitech.revealing.auth.service;


import com.delitech.revealing.auth.util.CustomAuthTokenDto;
import com.delitech.revealing.commons.MessageComponent;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageComponent messageComponent;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String type = ((CustomAuthTokenDto) authentication).getType();

        Optional<UserEntity> user = userRepository.findByEmailAndType(username, type);

        if (user.isEmpty()) {
            throw new BadCredentialsException(messageComponent.getBundleMessage("auth.failed"));
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new BadCredentialsException(messageComponent.getBundleMessage("auth.user.not.found"));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(type));
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomAuthTokenDto.class);
    }
}
