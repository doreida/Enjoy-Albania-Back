package net.sparklab.AirBNBReservation.converters;


import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.model.Guest;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.model.Status;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

@Component
@AllArgsConstructor
public class ReservationDTOToReservation implements Converter<ReservationDTO, Reservation> {

    private final GuestDTOtoGuest guestDTOtoGuest;
    private final GuestRepository guestRepository;

    @Override
    public Reservation convert(ReservationDTO source) {

        if (source!=null){
            Reservation reservation = new Reservation();
            if (source.getId()!=null){
                reservation.setId(source.getId());
            }
            //TODO Earnings to currency


            String earning = source.getEarnings().replace("€","").substring(1);
            reservation.setEarning(new BigDecimal(earning));
            reservation.setCurrency(Currency.getInstance("EUR"));
            reservation.setListing(source.getListing());
            //TODO dates to be saved as long
            reservation.setBookedDate(LocalDate.parse(source.getBookedDate()));
            reservation.setEndDate(LocalDate.parse(source.getEndDate(),DateTimeFormatter.ofPattern("M/d/uuuu")));
            reservation.setStartDate(LocalDate.parse(source.getStartDate(),DateTimeFormatter.ofPattern("M/d/uuuu")));
            reservation.setCreatedDate(LocalDate.now());

            reservation.setNoAdults(source.getNrAdults());
            reservation.setNoInfants(source.getNrInfants());
            reservation.setNoChildren(source.getNrChildren());
            reservation.setConfirmationCode(source.getConfirmCode());
            reservation.setNoNights(source.getNrNights());
            reservation.setListing(source.getListing());
            //TODO set guest by name and surname

            String fullName = source.getGuestName();
            String[] nameParts = fullName.split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts[1];
            if (guestRepository.findByFirstNameAndLastName(firstName,lastName)==null) {
                GuestDTO guestDTO = new GuestDTO();
                guestDTO.setFirstname(firstName);
                guestDTO.setLastname(lastName);
                guestDTO.setStatus(source.getStatus()=="Past guest" ? "Past_Guest" : null);
                guestDTO.setContact(source.getContact());
                Guest guest = guestRepository.save(guestDTOtoGuest.convert(guestDTO));
                reservation.setGuest(guest);
            }else {
                reservation.setGuest(guestRepository.findByFirstNameAndLastName(firstName,lastName));
            }
            return reservation;

        }
        return null;
    }
}
