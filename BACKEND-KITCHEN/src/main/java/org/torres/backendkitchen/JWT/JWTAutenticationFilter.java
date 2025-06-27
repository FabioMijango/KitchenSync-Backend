package org.torres.backendkitchen.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.torres.backendkitchen.Exception.ExceptionUtils.JwtAuthenticationException;

import java.io.IOException;

import static org.torres.backendkitchen.util.Constants.*;

@Component
public class JWTAutenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtService jwtService;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return path.equals(AUTH_CONTROLLER + LOGIN)
                || (method.equals("GET") && (
                path.equals(KITCHEN + DISH_CONTROLLER + GET_ALL)
                        || path.equals(KITCHEN + DISH_CONTROLLER + GET_BY_NAME)
        ));
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            final String token = getTokenFromRequest(request);

            // Si el token es nulo, lanzamos una excepción personalizada
            if (token == null) {
                throw new JwtAuthenticationException("Token no proporcionado.");
            }

            // Extraemos el email del token y validamos
            String email = jwtService.extractUsername(token);

            if (email == null || !jwtService.isTokenValid(token, userDetailsService.loadUserByUsername(email))) {
                throw new JwtAuthenticationException("Token inválido o expirado.");
            }

            // Cargamos los detalles del usuario y configuramos la autenticación
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // Establecemos los detalles de autenticación y el contexto de seguridad
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (JwtAuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + e.getMessage() + "\"}");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\", \"message\": \"Token invalido\"}");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\", \"message\": \"Error inesperado\"}");

        }
    }
}