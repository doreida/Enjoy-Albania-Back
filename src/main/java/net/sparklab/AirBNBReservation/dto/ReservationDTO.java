package net.sparklab.AirBNBReservation.dto;

import lombok.Data;


@Data
public class ReservationDTO {

    private Long id;
    private Long createdDate;
    //    private long lastModifiedDate
    private String confirmationCode;
    private int noAdults;
    private int noChildren;
    private int noInfants;
    private Long startDate;
    private Long endDate;
    private int noNights;
    private String listing;
    private String earning;
    private GuestDTO guest;
}
