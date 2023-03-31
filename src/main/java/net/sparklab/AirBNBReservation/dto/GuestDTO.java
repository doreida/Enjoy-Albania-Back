package net.sparklab.AirBNBReservation.dto;

import lombok.Data;

@Data
public class GuestDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String contact;
    private String status;

}
