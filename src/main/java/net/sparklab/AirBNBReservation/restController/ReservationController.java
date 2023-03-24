package net.sparklab.AirBNBReservation.restController;


import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.services.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/AirBNBReservation/reservation")
@CrossOrigin("*")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/findAll/{pageNumber}/{pageSize}")
    public Page<ReservationDTO> findAll(@PathVariable int pageNumber, @PathVariable int pageSize, @RequestParam String sortBy, @RequestParam String sortDir){
        return reservationService.findAll(pageNumber, pageSize, sortBy, sortDir);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadData(@RequestParam("file") MultipartFile file) throws Exception {
        return reservationService.uploadData(file);
    }

    @PostMapping
    public ResponseEntity<?> saveOrUpdate(@RequestBody ReservationDTO reservationDTO){
        return reservationService.saveOrUpdate(reservationDTO);
    }

}
