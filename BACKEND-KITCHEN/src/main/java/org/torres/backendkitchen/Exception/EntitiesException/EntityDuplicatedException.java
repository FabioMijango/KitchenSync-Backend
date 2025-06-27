package org.torres.backendkitchen.Exception.EntitiesException;

public class EntityDuplicatedException extends RuntimeException{
    public EntityDuplicatedException(String message) {
        super(message);
    }

    public EntityDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
