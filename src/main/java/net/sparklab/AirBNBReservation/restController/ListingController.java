package net.sparklab.AirBNBReservation.restController;


import net.sparklab.AirBNBReservation.dto.ListingDTO;
import net.sparklab.AirBNBReservation.dto.ReservationDTO;
import net.sparklab.AirBNBReservation.services.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("enjoyAlbania/listing")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/allListing")
    public List<ListingDTO> findAllListings(){
        return listingService.findAll();
    }


    @GetMapping("{id}")
    public ListingDTO findListingById(@PathVariable String id){
        return listingService.findById(id);
    }


    @PostMapping
    public ResponseEntity<?> saveOrUpdate(@RequestBody ListingDTO listingDTO){
        return listingService.saveOrUpdate(listingDTO);
    }


}
