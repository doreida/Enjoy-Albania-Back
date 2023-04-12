package net.sparklab.AirBNBReservation.dto;

import lombok.Data;

import java.util.OptionalDouble;

@Data
public class ReportDTO {

 private OptionalDouble avg_Length_Stay;

 private OptionalDouble avg_Guest;

 private OptionalDouble avg_Anticipation;

 private String  percentage_of_reservations_with_children;

 private String percentage_of_reservations_with_infants;

 private double percentage_of_occupancy_listing_between_dates;



}
