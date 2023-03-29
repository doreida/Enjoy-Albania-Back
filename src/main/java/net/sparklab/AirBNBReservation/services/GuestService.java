package net.sparklab.AirBNBReservation.services;

import net.sparklab.AirBNBReservation.converters.GuestToGuestDTO;
import net.sparklab.AirBNBReservation.converters.ReservationToReservationDTO;
import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.exceptions.NotFoundException;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestToGuestDTO toGuestDTO;
    private final ReservationRepository reservationRepository;

    private final ReservationToReservationDTO toReservationDTO;

    public GuestService(GuestRepository guestRepository, GuestToGuestDTO toGuestDTO, ReservationRepository reservationRepository, ReservationToReservationDTO toReservationDTO) {
        this.guestRepository = guestRepository;
        this.toGuestDTO = toGuestDTO;
        this.reservationRepository = reservationRepository;
        this.toReservationDTO = toReservationDTO;
    }

    public GuestDTO findById(String id){
        Long parseId;
        parseId=Long.parseLong(id);
        return toGuestDTO.convert(guestRepository.findById(parseId).orElseThrow(()-> new NotFoundException("Record with id: "+ id +"not found!")));
    }

    public List<GuestDTO> findAll(){
        return guestRepository.findAll().stream().map(guest -> toGuestDTO.convert(guest)).collect(Collectors.toList());
    }

    public List<ReservationDTO> findReservationsByGuest(String id) {
        Long parseId;
        try {
            parseId = Long.parseLong(id);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Reservation id: \"" + id + "\" can't be parsed!");
        }
        return reservationRepository.findByGuestId(parseId).stream().map(reservation->toReservationDTO.convert(reservation)).collect(Collectors.toList());
    }
}
