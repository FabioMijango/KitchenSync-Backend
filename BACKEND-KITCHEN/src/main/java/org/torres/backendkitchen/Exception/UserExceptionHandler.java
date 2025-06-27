package org.torres.backendkitchen.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.torres.backendkitchen.Exception.ExceptionUser.UserLoginException;
import org.torres.backendkitchen.Exception.ExceptionUser.UserRegisterException;
import org.torres.backendkitchen.util.GenericExceptionResponse;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserRegisterException.class)
    public ResponseEntity<GenericExceptionResponse> handleUserRegisterException(UserRegisterException exception, HttpServletRequest request){
        return GenericExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }

    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<GenericExceptionResponse> handleUserLoginException(UserLoginException exception, HttpServletRequest request) {
        return GenericExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }

}
