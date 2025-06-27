package org.torres.backendkitchen.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.torres.backendkitchen.Domain.DTO.AuthResponse;
import org.torres.backendkitchen.Domain.DTO.LoginRequest;
import org.torres.backendkitchen.Domain.DTO.RegisterRequest;
import org.torres.backendkitchen.Domain.DTO.RequestResponse;


public interface iAuthService {
    AuthResponse login(LoginRequest request);
    RequestResponse register(RegisterRequest request);
}
