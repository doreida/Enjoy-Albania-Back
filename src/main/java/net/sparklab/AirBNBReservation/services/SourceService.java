package net.sparklab.AirBNBReservation.services;


import net.sparklab.AirBNBReservation.converters.SourceToSourceDTO;
import net.sparklab.AirBNBReservation.dto.SourceDTO;
import net.sparklab.AirBNBReservation.exceptions.NotFoundException;
import net.sparklab.AirBNBReservation.model.Listing;
import net.sparklab.AirBNBReservation.model.Source;
import net.sparklab.AirBNBReservation.repositories.SourceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SourceService {


    private final SourceRepository sourceRepository;
    private final SourceToSourceDTO toSourceDTO;

    public SourceService(SourceRepository sourceRepository, SourceToSourceDTO toSourceDTO) {
        this.sourceRepository = sourceRepository;
        this.toSourceDTO = toSourceDTO;
    }




    public List<SourceDTO> findAll() {

       return sourceRepository.findAll().stream().map(source -> toSourceDTO.convert(source)).collect(Collectors.toList());


    }



    public SourceDTO findById(String id) {

        Long parseId;
        try {
            parseId = Long.parseLong(id);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Reservation id: \"" + id + "\" can't be parsed!");
        }
        return toSourceDTO.convert(sourceRepository.findById(parseId).orElseThrow(()-> new NotFoundException("Record with id: "+ id +"not found!")));
    }

    public ResponseEntity<?> saveOrUpdate(SourceDTO sourceDTO) {

        try{
            if(sourceRepository.existsBySource(sourceDTO.getSource())){
                return new ResponseEntity<>("There is already a source  b with this name", HttpStatus.BAD_REQUEST);}
            else{
              Source source=new Source();
                if(sourceDTO.getId()!=null){
                    source .setId(sourceDTO.getId());}
                source.setSource(sourceDTO.getSource());
                sourceRepository.save(source);
                return new ResponseEntity<>("Source saved successfully", HttpStatus.OK);
            }
        }

        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
