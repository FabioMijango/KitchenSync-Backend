package org.torres.backendkitchen.Exception.EntitiesException;

public class EntityNotValidException extends RuntimeException{
    public EntityNotValidException(String message) {
        super(message);
    }

    public EntityNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
