package net.sparklab.AirBNBReservation.dto;

import lombok.Data;

import java.util.OptionalDouble;

@Data
public class ReportDTO {

 private String avg_Length_Stay;

 private String avg_Guest;

 private String avg_Anticipation;

 private String  percentage_of_reservations_with_children;

 private String percentage_of_reservations_with_infants;

 private String percentage_of_occupancy_listing_between_dates;



}
