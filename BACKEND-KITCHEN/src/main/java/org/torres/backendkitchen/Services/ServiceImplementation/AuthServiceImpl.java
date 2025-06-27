package org.torres.backendkitchen.Services.ServiceImplementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.torres.backendkitchen.Domain.DTO.AuthResponse;
import org.torres.backendkitchen.Domain.DTO.LoginRequest;
import org.torres.backendkitchen.Domain.DTO.RegisterRequest;
import org.torres.backendkitchen.Domain.DTO.RequestResponse;
import org.torres.backendkitchen.Exception.ExceptionUser.UserLoginException;
import org.torres.backendkitchen.Exception.ExceptionUser.UserRegisterException;
import org.torres.backendkitchen.JWT.JwtService;
import org.torres.backendkitchen.Domain.Entity.User;
import org.torres.backendkitchen.Repository.iUserRepository;
import org.torres.backendkitchen.Services.iAuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements iAuthService {

    private final iUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UserLoginException("Usuario y/o contraseña inválidos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserLoginException("Usuario y/o contraseña inválidos");
        }

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .message("Inicio de sesión exitoso")
                .build();
    }

    @Override
    public RequestResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserRegisterException("El email ya existe");
        }

        User user = User.builder()
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .role(request.getRole())
                            .build();
        userRepository.save(user);

        return RequestResponse.builder()
                .message("Usuario registrado exitosamente")
                .build();
    }
}