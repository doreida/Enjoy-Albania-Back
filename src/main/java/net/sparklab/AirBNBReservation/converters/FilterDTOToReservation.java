package net.sparklab.AirBNBReservation.converters;


import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.FilterDTO;
import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.model.Guest;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;

@Component
@AllArgsConstructor
public class FilterDTOToReservation implements Converter<FilterDTO, Reservation> {
    private final GuestRepository guestRepository;
    private final GuestDTOtoGuest guestDTOtoGuest;
    @Override
    public Reservation convert(FilterDTO source) {
        if (source!=null){
            Reservation reservation = new Reservation();

            reservation.setListing(source.getListing());
            reservation.setConfirmationCode(source.getConfirmationCode());

            if (source.getGuestName()!=null) {
                String fullName = source.getGuestName();
                String[] nameParts = fullName.split(" ");

                String firstName = nameParts[0];
                String lastName = "";
                if (nameParts.length!=1) {
                    lastName = fullName.replace(firstName + " ", "");
                }
                if (guestRepository.findByFirstNameAndLastName(firstName, lastName) == null) {
                    GuestDTO guestDTO = new GuestDTO();
                    guestDTO.setFirstname(firstName);
                    guestDTO.setLastname(lastName);
                    if (source.getStatus() != null) {
                        guestDTO.setStatus(source.getStatus().equals("Past guest") ? "Past_Guest" : null);
                    }
                    guestDTO.setContact(source.getContact());
                    Guest guest = guestDTOtoGuest.convert(guestDTO);
                    reservation.setGuest(guest);
                } else {
                    reservation.setGuest(guestRepository.findByFirstNameAndLastName(firstName, lastName));
                }
            }

            return reservation;

        }
        return null;

    }
}
