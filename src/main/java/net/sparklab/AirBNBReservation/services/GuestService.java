package net.sparklab.AirBNBReservation.services;

import net.sparklab.AirBNBReservation.converters.GuestToGuestDTO;
import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.exceptions.NotFoundException;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestToGuestDTO toGuestDTO;

    public GuestService(GuestRepository guestRepository, GuestToGuestDTO toGuestDTO) {
        this.guestRepository = guestRepository;
        this.toGuestDTO = toGuestDTO;
    }

    public GuestDTO findById(String id){
        Long parseId;
        parseId=Long.parseLong(id);
        return toGuestDTO.convert(guestRepository.findById(parseId).orElseThrow(()-> new NotFoundException("Record with id: "+ id +"not found!")));
    }

    public List<GuestDTO> findAll(){
        return guestRepository.findAll().stream().map(guest -> toGuestDTO.convert(guest)).collect(Collectors.toList());
    }
}
