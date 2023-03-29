package net.sparklab.AirBNBReservation.restController;

import lombok.RequiredArgsConstructor;
import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.services.GuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("enjoyAlbania/guest")
@RequiredArgsConstructor
@CrossOrigin("*")
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
