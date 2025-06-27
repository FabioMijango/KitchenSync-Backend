package org.torres.backendkitchen.Exception.ExceptionUtils;


/*
    * This class represents a custom exception for JWT authentication errors.
 */
public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
