package com.delitech.revealing.auth.controller;

import com.delitech.revealing.auth.service.AuthService;
import com.delitech.revealing.auth.util.AuthResponse;
import com.delitech.revealing.auth.util.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controlador inicio de sesion
 */
@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Servicio que retorna token inicio de sesion
     *
     * @param request {@link LoginRequest}
     * @return {@link AuthResponse}
     */
    @PostMapping
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
