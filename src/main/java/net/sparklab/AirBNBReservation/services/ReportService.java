package net.sparklab.AirBNBReservation.services;

import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.converters.FilterDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationDTOToReservation;
import net.sparklab.AirBNBReservation.converters.ReservationToReservationDTO;
import net.sparklab.AirBNBReservation.dto.FilterDTO;
import net.sparklab.AirBNBReservation.dto.ReportDTO;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.repositories.ReservationRepository;
import net.sparklab.AirBNBReservation.repositories.SourceRepository;
import net.sparklab.AirBNBReservation.specifications.ReservationSpecification;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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



    public OptionalDouble avgGuests(List<Reservation> reservations){

        OptionalDouble avg= reservations.stream().mapToDouble(Reservation::getNoGuests).average();
        return avg;

    }









}
