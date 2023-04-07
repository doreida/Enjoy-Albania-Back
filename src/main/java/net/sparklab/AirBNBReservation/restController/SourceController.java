package net.sparklab.AirBNBReservation.restController;

import net.sparklab.AirBNBReservation.dto.ListingDTO;
import net.sparklab.AirBNBReservation.dto.SourceDTO;
import net.sparklab.AirBNBReservation.services.SourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("enjoyAlbania/source")
public class SourceController {

private final SourceService sourceService;


    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }
    @GetMapping
    public List<SourceDTO> findAllSources(){
        return sourceService.findAll();
    }

    @GetMapping("{id}")
    public SourceDTO findSourceById(@PathVariable String id){
        return sourceService.findById(id);
    }


    @PostMapping
    public ResponseEntity<?> saveOrUpdate(@RequestBody SourceDTO sourceDTO){
        return sourceService.saveOrUpdate(sourceDTO);
    }


}
