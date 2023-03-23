package net.sparklab.AirBNBReservation.restController;

import lombok.RequiredArgsConstructor;
import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.services.GuestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("enjoyAlbania/guest")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;
    private final GuestRepository guestRepository;
    @GetMapping("/{id}")
    public GuestDTO findGuestById(@PathVariable String id){
        return guestService.findById(id);
    }
    @GetMapping("/allGuests")
    public List<GuestDTO> findAllGuests(){
        return guestService.findAll();
    }

    @GetMapping("/{id}/reservations")
    public List<ReservationDTO> findReservationsByGuest(@PathVariable String id){
        return guestService.findReservationsByGuest(id);
    }



}
