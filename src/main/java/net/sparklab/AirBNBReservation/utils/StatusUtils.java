package net.sparklab.AirBNBReservation.utils;

import net.sparklab.AirBNBReservation.exceptions.NotFoundException;
import net.sparklab.AirBNBReservation.model.Status;

public class StatusUtils {

    public static Status getStatus(String status){
        if(status.equalsIgnoreCase(Status.Past_Guest.name())){
            return Status.Past_Guest;
        }else if (status.equalsIgnoreCase(Status.New_Guest.name())){
            return Status.New_Guest;
        }
        else{
            throw new NotFoundException("This status is not valid.Select a status from the list!");
        }
    }
}
