package net.sparklab.AirBNBReservation.converters;


import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.FilterDTO;

import net.sparklab.AirBNBReservation.model.Guest;
import net.sparklab.AirBNBReservation.model.Listing;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.model.Status;
import net.sparklab.AirBNBReservation.repositories.ListingRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FilterDTOToReservation implements Converter<FilterDTO, Reservation> {
    private final GuestDTOtoGuest guestDTOtoGuest;
    private final ListingRepository listingRepository;

    @Override
    public Reservation convert(FilterDTO source) {
        if (source!=null){
            Reservation reservation = new Reservation();

            reservation.setListing(listingRepository.findByListing(source.getListing()));
            reservation.setConfirmationCode(source.getConfirmationCode());

            if (source.getGuestName()!=null) {
                Guest guest = new Guest();

                String fullName = source.getGuestName();
                String[] nameParts = fullName.split(" ");
                String firstName = nameParts[0];
                String lastName = "";


                if (nameParts.length!=1) {
                    lastName = fullName.replace(firstName + " ", "");
                }
                guest.setFirstName(firstName);
                guest.setLastName(lastName);
                if (source.getStatus() != null) {
                    guest.setStatus(source.getStatus().equals("Past guest") || source.getStatus().equals("Past_Guest")  ? Status.Past_Guest : null);
                }
                guest.setContact(source.getContact());

                reservation.setGuest(guest);
            }
            return reservation;
        }
        return null;

    }
}
