package net.sparklab.AirBNBReservation.converters;

import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.model.Reservation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ReservationToReservationDTO implements Converter<Reservation, ReservationDTO> {
    @Override
    public ReservationDTO convert(Reservation source) {
        if (source!=null){
            ReservationDTO reservationDTO = new ReservationDTO();
            if (source.getId()!=null) {
                reservationDTO.setId(source.getId());
            }
            reservationDTO.setCreatedDate(source.getCreatedDate());
            reservationDTO.setEndDate(source.getEndDate());
            reservationDTO.setStartDate(source.getStartDate());
            reservationDTO.setEarning(source.getEarning().toString());
//            reservationDTO.setGuest();
            reservationDTO.setListing(source.getListing());
            reservationDTO.setConfirmationCode(source.getConfirmationCode());
            reservationDTO.setNoAdults(source.getNoAdults());
            reservationDTO.setNoChildren(source.getNoChildren());
            reservationDTO.setNoInfants(source.getNoInfants());
            reservationDTO.setNoNights(source.getNoNights());



            return reservationDTO;
        }

        return null;
    }
}
