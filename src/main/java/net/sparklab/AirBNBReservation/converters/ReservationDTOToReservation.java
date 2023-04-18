package net.sparklab.AirBNBReservation.converters;


import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.model.Guest;
import net.sparklab.AirBNBReservation.model.Listing;
import net.sparklab.AirBNBReservation.model.Reservation;
import net.sparklab.AirBNBReservation.model.Source;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.repositories.ListingRepository;
import net.sparklab.AirBNBReservation.repositories.ReservationRepository;
import net.sparklab.AirBNBReservation.repositories.SourceRepository;
import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

@Component
@AllArgsConstructor
public class ReservationDTOToReservation implements Converter<ReservationDTO, Reservation> {
    private final GuestDTOtoGuest guestDTOtoGuest;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final ListingRepository listingRepository;
    private final SourceRepository sourceRepository;

    @SneakyThrows
    @Override
    public Reservation convert(ReservationDTO source) {

        if (source!=null){

            //Return null if another reservation with the same confirmation code exists
            if (reservationRepository.existsByConfirmationCode(source.getConfirmationCode()) && source.getId() == null){
                return null;
            }

            Reservation reservation = new Reservation();

            if (source.getId()!=null){
                reservation.setId(source.getId());
            }

            //Earning from string to Currency and Decimal
            String earning ;

            try {
                earning = source.getEarning().replace("€","").replace(",","");
                reservation.setEarning(new BigDecimal(earning));
            }catch (NumberFormatException e){
                earning = source.getEarning().replace("€","").replace(",","").substring(1);
                reservation.setEarning(new BigDecimal(earning));
            }

            reservation.setCurrency(Currency.getInstance("EUR"));


            //Defining 2 date formats for csv and for manual reservation adding
            DateTimeFormatter standardFormat = DateTimeFormatter.ofPattern("yyyy-M-d");
            DateTimeFormatter csvFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
            DateTimeFormatter bookedDateCsv = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            //Dates from string to LocalDate from csv
            try {
                reservation.setBookedDate(LocalDate.parse(source.getBookedDate(), csvFormat));
            }catch (DateTimeParseException e){
                try {
                    reservation.setBookedDate(LocalDate.parse(source.getBookedDate(), standardFormat));
                }catch (DateTimeParseException e2){
                    try {
                        reservation.setBookedDate(LocalDate.parse(source.getBookedDate(), bookedDateCsv));
                    }catch (DateTimeParseException e3){
                        throw e3;
                    }
                }
            }

            try {
                reservation.setEndDate(LocalDate.parse(source.getEndDate(), csvFormat));
            }catch (DateTimeParseException e){
                try {
                    reservation.setEndDate(LocalDate.parse(source.getEndDate(), standardFormat));
                }catch (DateTimeParseException e2){
                    throw e2;
                }
            }

            try {
                reservation.setStartDate(LocalDate.parse(source.getStartDate(), csvFormat));
            }catch (DateTimeParseException e){
                try {
                    reservation.setStartDate(LocalDate.parse(source.getStartDate(), standardFormat));
                }catch (DateTimeParseException e2){
                    throw e2;
                }
            }

            if (source.getId()!=null){
                reservation.setCreatedDate(reservationRepository.findById(source.getId()).get().getCreatedDate());
            }else {
                reservation.setCreatedDate(LocalDateTime.now());
            }
            reservation.setNoNights(source.getNrNights());
            reservation.setNoAdults(source.getNrAdults());
            reservation.setNoInfants(source.getNrInfants());
            reservation.setNoChildren(source.getNrChildren());
            reservation.setConfirmationCode(source.getConfirmationCode());

            //Splitting Full name of the Guest and finding if a Guest Record exist, if not create a new guest
            String fullName = source.getGuestName();
            String[] nameParts = fullName.split(" ");

            String firstName = nameParts[0];
            String lastName = fullName.replace(firstName+" ","");
            Guest guest = guestRepository.findByFirstNameAndLastName(firstName,lastName);
            if (guest==null) {
                GuestDTO guestDTO = new GuestDTO();
                guestDTO.setFirstname(firstName);
                guestDTO.setLastname(lastName);
                if (source.getStatus()!=null) {
                    guestDTO.setStatus(source.getStatus().equals("Past guest") || source.getStatus().equals("Past_Guest") ? "Past_Guest" : null);
                }
                guestDTO.setContact(source.getContact());
                Guest guest1 = guestDTOtoGuest.convert(guestDTO);
                reservation.setGuest(guest1);
            }else {
                reservation.setGuest(guest);
            }

            Listing listing=listingRepository.findByListing(source.getListing());
            if(listing ==null){
                Listing savelisting =new Listing();
                 savelisting.setListing(source.getListing());
                 listingRepository.save(savelisting);
                 reservation.setListing(savelisting);
            }
            else{
            reservation.setListing(listing);
            }

            Source sourceFind= sourceRepository.findSourcesBySource(source.getSource());
            if(source.getSource()==null){
                reservation.setSource(sourceRepository.findSourcesBySource("AirBNBReservation"));
            }
            else if(sourceFind==null){
                Source sourceToSave = new Source();
                sourceToSave.setSource(source.getSource());
                sourceRepository.save(sourceToSave);
                reservation.setSource(sourceToSave);
            }
            else{
                reservation.setSource(sourceFind);
            }
            reservation.setAnticipation(reservation.getBookedDate().until(reservation.getStartDate(), ChronoUnit.DAYS));
            reservation.setNoGuests(reservation.getNoAdults()+ reservation.getNoChildren()+ reservation.getNoInfants());
            return reservation;
        }
        return null;
    }

}
