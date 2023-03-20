package net.sparklab.AirBNBReservation.dto;

import lombok.Data;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.model.Status;

import java.util.List;

@Data
public class GuestDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String contact;
    private String status;

}
