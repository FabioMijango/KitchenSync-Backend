package org.torres.backendkitchen.Exception.ExceptionUser;

public class UserLoginException extends RuntimeException{
    public UserLoginException(String message) {
        super(message);
    }

    public UserLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
