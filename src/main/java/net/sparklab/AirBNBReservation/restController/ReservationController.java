package net.sparklab.AirBNBReservation.restController;


import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.services.ReservationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/findAll")
    public List<ReservationDTO> findAll(){
        return reservationService.findAll();
    }

    @PostMapping("/uploadFile")
    public List<ReservationDTO> uploadData(@RequestParam("file") MultipartFile file) throws Exception {
        return reservationService.uploadData(file);
    }


}
