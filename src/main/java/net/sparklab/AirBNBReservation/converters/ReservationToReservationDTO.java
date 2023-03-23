package net.sparklab.AirBNBReservation.converters;

import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.model.Reservation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;


@Component
@AllArgsConstructor
public class ReservationToReservationDTO implements Converter<Reservation, ReservationDTO> {

    private final GuestToGuestDTO toGuestDTO;
    @Override
    public ReservationDTO convert(Reservation source) {
        if (source!=null){
            ReservationDTO reservationDTO = new ReservationDTO();
            if (source.getId()!=null) {
                reservationDTO.setId(source.getId());
            }
            reservationDTO.setCreatedDate(source.getCreatedDate().toString());
            reservationDTO.setEndDate(source.getEndDate().toString());
            reservationDTO.setStartDate(source.getStartDate().toString());
            reservationDTO.setEarnings(NumberFormat.getCurrencyInstance().format(source.getEarning()).toString());

            reservationDTO.setGuest(toGuestDTO.convert(source.getGuest()));
            reservationDTO.setListing(source.getListing());
            reservationDTO.setConfirmCode(source.getConfirmationCode());
            reservationDTO.setNrAdults(source.getNoAdults());
            reservationDTO.setNrChildren(source.getNoChildren());
            reservationDTO.setNrInfants(source.getNoInfants());
            reservationDTO.setNrNights(source.getNoNights());



            return reservationDTO;
        }

        return null;
    }
}
