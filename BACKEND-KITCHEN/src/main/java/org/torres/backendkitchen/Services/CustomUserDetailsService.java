package org.torres.backendkitchen.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.torres.backendkitchen.Domain.Entity.User;
import org.torres.backendkitchen.Repository.iUserRepository;

import java.util.Collections;
// Clase para manejar la autenticación de usuarios
// y la carga de detalles del usuario desde la base de datos.
// Sobre escribe el método loadUserByUsername para buscar un usuario por su email.


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final iUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );

    }
}
