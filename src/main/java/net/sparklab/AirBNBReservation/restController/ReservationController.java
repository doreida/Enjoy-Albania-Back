package net.sparklab.AirBNBReservation.restController;


import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.FilterDTO;
import net.sparklab.AirBNBReservation.dto.ReportDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.services.ReportService;
import net.sparklab.AirBNBReservation.services.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/enjoyAlbania/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReportService reportService;

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadData(@RequestParam("file") MultipartFile file) throws Exception {
        return reservationService.uploadData(file);
    }

    @PostMapping
    public ResponseEntity<?> saveOrUpdate(@RequestBody ReservationDTO reservationDTO){
        return reservationService.saveOrUpdate(reservationDTO);
    }

    @GetMapping("/findAll")
    public Page<ReservationDTO> findAll(@ModelAttribute FilterDTO filterDto){
        return reservationService.findAll(filterDto);
    }

    @GetMapping("/findAllCalendar")
    public List<ReservationDTO> findAll(){
        return reservationService.findAllCalendar();
    }

    @GetMapping("/{id}")
    public ReservationDTO findById( @PathVariable String id){
        return reservationService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        return reservationService.delete(id);
    }


    @GetMapping("/reports")
     public ResponseEntity<?> findReport(@ModelAttribute FilterDTO filterDTO){

        return reservationService.findReport(filterDTO);
     }
}
