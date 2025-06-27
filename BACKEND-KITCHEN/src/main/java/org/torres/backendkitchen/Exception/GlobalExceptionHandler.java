package org.torres.backendkitchen.Exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.torres.backendkitchen.Exception.ExceptionUtils.JwtAuthenticationException;
import org.torres.backendkitchen.util.GenericExceptionResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<GenericExceptionResponse> handleJwtException(JwtAuthenticationException exception, HttpServletRequest request) {
        return GenericExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return GenericExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Malformed JSON request")
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<GenericExceptionResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception, HttpServletRequest request) {
        return GenericExceptionResponse.builder()
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .message("Unsupported media type")
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericExceptionResponse> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        return GenericExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Illegal argument or not valid")
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GenericExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        return GenericExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid argument type: " + exception.getMessage().substring(exception.getMessage().indexOf(";") + 2))
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build().buildResponse();
    }
}
