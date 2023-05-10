package net.sparklab.AirBNBReservation.services;

import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.converters.FilterDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationToReservationDTO;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.repositories.ListingRepository;
import net.sparklab.AirBNBReservation.repositories.ReservationRepository;
import net.sparklab.AirBNBReservation.repositories.SourceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.OptionalDouble;

@Service
@AllArgsConstructor
public class ReportService {

    public final ReservationRepository reservationRepository;
    public final ReservationToReservationDTO toReservationDTO;
    public final ReservationDTOToReservation toReservation;
    public final FilterDTOToReservation filterDTOToReservation;
    public final GuestRepository guestRepository;
    private final SourceRepository sourceRepository;
    private final ListingRepository listingRepository;


    public String avgGuests(List<Reservation> reservations) {

       double avg = reservations.stream().mapToDouble(Reservation::getNoGuests).average().getAsDouble();
        return String.format("%,.1f",avg);

    }

    public String avgAnticipation(List<Reservation> reservations) {
        double avgAnticipation = reservations.stream().mapToDouble(Reservation::getAnticipation).average().getAsDouble();
        return String.format("%,.2f",avgAnticipation);
    }


    public String avgLength_Stay(List<Reservation> reservations) {

       double avg_Stay = reservations.stream().mapToDouble(Reservation::getNoNights).average().getAsDouble();
        return  String.format("%,.2f",avg_Stay);
    }


    public String percentage_reservation_with_children(List<Reservation> reservations) {

        int listlength = reservations.size();
        long listwithchildren = reservations.stream().filter(reservation -> reservation.getNoChildren() > 0).count();
        double percentage = (double) listwithchildren / listlength * 100;

        return String.format("%,.2f", percentage);
    }

    public String percentageOfReservationsWithInfants(List<Reservation> reservations) {
        int listLength = reservations.size();
        long listWithInfants = reservations.stream().filter(reservation -> reservation.getNoInfants() > 0).count();
        double percentage = (double) listWithInfants / listLength * 100;
        return String.format("%,.2f", percentage);
    }


    public String percentageOfOccupancyPerListingAndDates(List<Reservation> reservations, LocalDate start, LocalDate end, String listing) {

        long count=0L;
        if (reservations.size() > 0) {
            // int nr_properties=reservations.stream().map(Reservation::getListing).distinct().collect(Collectors.toList()).size();
            int nr_properties = listingRepository.findAllByListingContaining(listing).size();
            for (Reservation reservation : reservations) {
                if (reservation.getEndDate().compareTo(end) <= 0) {
                    count = count + reservation.getNoNights();
                } else if (reservation.getEndDate().compareTo(end) > 0) {
                    count =count + reservation.getStartDate().until(end, ChronoUnit.DAYS)+1;
                }
            }
            int listLength = reservations.size();
            long day_diff = start.until(end, ChronoUnit.DAYS) + 1;
            double maximum_reservations = nr_properties * day_diff;
            double percentage = (double) count / maximum_reservations * 100;
            return String.format("%,.2f", percentage);

        } else {
            return String.format("%,.2f", 0);
        }

    }


}
