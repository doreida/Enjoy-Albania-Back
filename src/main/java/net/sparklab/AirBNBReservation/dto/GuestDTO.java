package net.sparklab.AirBNBReservation.dto;

import lombok.Data;
import net.sparklab.AirBNBReservation.model.Status;

@Data
public class GuestDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String contact;
    private Status status;

//    private List<Reservation> reservations;
}
