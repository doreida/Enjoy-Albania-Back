package net.sparklab.AirBNBReservation.converters;


import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.exceptions.EntityExistsException;
import net.sparklab.AirBNBReservation.model.Guest;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.repositories.ReservationRepository;
import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

@Component
@AllArgsConstructor
public class ReservationDTOToReservation implements Converter<ReservationDTO, Reservation> {

    private final GuestDTOtoGuest guestDTOtoGuest;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @SneakyThrows
    @Override
    public Reservation convert(ReservationDTO source) {

        if (source!=null){
            Reservation reservation = new Reservation();

            if (source.getId()!=null){
                reservation.setId(source.getId());
            }
            //Earning from string to Currency and Decimal
            String earning = source.getEarning().replace("€","").substring(1);
            reservation.setEarning(new BigDecimal(earning));
            reservation.setCurrency(Currency.getInstance("EUR"));

            //Dates from string to LocalDate from csv
            reservation.setBookedDate(LocalDate.parse(source.getBookedDate(), DateTimeFormatter.ofPattern("d/MM/uuuu")));
            reservation.setEndDate(LocalDate.parse(source.getEndDate(), DateTimeFormatter.ofPattern("M/d/uuuu")));
            reservation.setStartDate(LocalDate.parse(source.getStartDate(), DateTimeFormatter.ofPattern("M/d/uuuu")));


            if (source.getId()!=null){
                reservation.setCreatedDate(reservationRepository.findById(source.getId()).get().getCreatedDate());
            }else {
                reservation.setCreatedDate(LocalDateTime.now());
            }
            reservation.setListing(source.getListing());
            reservation.setNoNights(source.getNrNights());
            reservation.setNoAdults(source.getNrAdults());
            reservation.setNoInfants(source.getNrInfants());
            reservation.setNoChildren(source.getNrChildren());

            if (reservationRepository.existsByConfirmationCode(source.getConfirmCode())){
            throw new EntityExistsException("A record with confirmation code: "+ source.getConfirmCode() + " already exists");
            }else {
                reservation.setConfirmationCode(source.getConfirmCode());
            }

            //Splitting Full name of the Guest and finding if an Guest Record exist, if not create a new guest
            String fullName = source.getGuestName();
            String[] nameParts = fullName.split(" ");

            String firstName = nameParts[0];
            String lastName = fullName.replace(firstName+" ","");
            if (guestRepository.findByFirstNameAndLastName(firstName,lastName)==null) {
                GuestDTO guestDTO = new GuestDTO();
                guestDTO.setFirstname(firstName);
                guestDTO.setLastname(lastName);
                if (source.getStatus()!=null) {
                    guestDTO.setStatus(source.getStatus().equals("Past guest") ? "Past_Guest" : null);
                }
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
