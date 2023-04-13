package net.sparklab.AirBNBReservation.converters;

import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.model.Reservation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;


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
            reservationDTO.setBookedDate(source.getBookedDate().toString());


            Locale germanLocale = new Locale("de", "DE");
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(germanLocale);
            symbols.setDecimalSeparator('.');
            symbols.setGroupingSeparator(',');
            String pattern = "€#,##0.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            String formattedNumber = decimalFormat.format(source.getEarning());

            reservationDTO.setEarning(formattedNumber);

            reservationDTO.setGuest(toGuestDTO.convert(source.getGuest()));
            reservationDTO.setGuestName(source.getGuest().getFirstName() + " " + source.getGuest().getLastName());
            reservationDTO.setListing(source.getListing().getListing());
            reservationDTO.setConfirmationCode(source.getConfirmationCode());
            reservationDTO.setNrAdults(source.getNoAdults());
            reservationDTO.setNrChildren(source.getNoChildren());
            reservationDTO.setNrInfants(source.getNoInfants());
            reservationDTO.setNrNights(source.getNoNights());
            reservationDTO.setAnticipation(source.getAnticipation());

            if (source.getGuest().getStatus()!=null) {
                reservationDTO.setStatus(source.getGuest().getStatus().toString());
            }

            return reservationDTO;
        }

        return null;
    }
}
