package net.sparklab.AirBNBReservation.exceptions;

public class NotValidFileException extends RuntimeException{
    public NotValidFileException(){
        super();
    }
    public NotValidFileException(String message){
        super(message);
    }
}
