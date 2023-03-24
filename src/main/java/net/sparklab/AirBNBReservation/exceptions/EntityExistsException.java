package net.sparklab.AirBNBReservation.exceptions;

public class EntityExistsException extends RuntimeException{
    public EntityExistsException() {
        super();
    }

    public EntityExistsException(String message) {
        super(message);
    }
}