package com.delitech.revealing.auth.service;


import com.delitech.revealing.auth.jwt.JwtService;
import com.delitech.revealing.auth.util.AuthResponse;
import com.delitech.revealing.auth.util.CustomAuthTokenDto;
import com.delitech.revealing.auth.util.LoginRequest;
import com.delitech.revealing.commons.MessageComponent;
import com.delitech.revealing.entity.UserEntity;
import com.delitech.revealing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CustomAuthProvider customAuthProvider;
    private final MessageComponent messageComponent;

    public AuthResponse login(LoginRequest request) {
        customAuthProvider.authenticate(new CustomAuthTokenDto(request.getUsername(), request.getPassword(), request.getType()));

        UserEntity user = userRepository.findByEmailAndType(request.getUsername(), request.getType())
                .orElseThrow(() -> new IllegalArgumentException(messageComponent.getBundleMessage("auth.user.not.found")));

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

}
