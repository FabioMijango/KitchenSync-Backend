package org.torres.backendkitchen.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.torres.backendkitchen.Domain.DTO.AuthResponse;
import org.torres.backendkitchen.Domain.DTO.LoginRequest;
import org.torres.backendkitchen.Domain.DTO.RegisterRequest;
import org.torres.backendkitchen.Domain.DTO.RequestResponse;
import org.torres.backendkitchen.Services.iAuthService;

import static org.torres.backendkitchen.util.Constants.*;

@RestController
@RequestMapping(AUTH_CONTROLLER)
@RequiredArgsConstructor
public class AuthController {

    private final iAuthService authService;


    @PostMapping(LOGIN)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    // Mover a UserController
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping(REGISTER)
    public ResponseEntity<RequestResponse> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.register(request));
    }



}
