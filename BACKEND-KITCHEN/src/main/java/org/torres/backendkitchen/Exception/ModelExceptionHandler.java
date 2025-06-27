package org.torres.backendkitchen.Exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.torres.backendkitchen.Exception.EntitiesException.EntityDuplicatedException;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotFoundException;
import org.torres.backendkitchen.Exception.EntitiesException.EntityNotValidException;
import org.torres.backendkitchen.util.GenericExceptionResponse;

@RestControllerAdvice
public class ModelExceptionHandler {

    @ExceptionHandler(EntityNotValidException.class)
    public ResponseEntity<GenericExceptionResponse> handleEntityNotValidException(EntityNotValidException exception, HttpServletRequest request) {
        return GenericExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }

    @ExceptionHandler(EntityDuplicatedException.class)
    public ResponseEntity<GenericExceptionResponse> handleEntityDuplicatedException(EntityDuplicatedException exception, HttpServletRequest request) {
        return GenericExceptionResponse.builder()
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GenericExceptionResponse> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest request) {
        return GenericExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }

}
