package net.sparklab.AirBNBReservation.services;


import net.sparklab.AirBNBReservation.converters.ListingToListingDTO;
import net.sparklab.AirBNBReservation.dto.ListingDTO;
import net.sparklab.AirBNBReservation.exceptions.NotFoundException;
import net.sparklab.AirBNBReservation.model.Listing;
import net.sparklab.AirBNBReservation.repositories.ListingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingToListingDTO toListingDTO;

    public ListingService(ListingRepository listingRepository, ListingToListingDTO toListingDTO) {
        this.listingRepository = listingRepository;

        this.toListingDTO = toListingDTO;
    }

    public List<ListingDTO> findAll() {

        return listingRepository.findAll().stream().map(listing -> toListingDTO.convert(listing)).collect(Collectors.toList());

    }



    public ListingDTO findById(String id) {

        Long parseId;
        try {
            parseId = Long.parseLong(id);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Reservation id: \"" + id + "\" can't be parsed!");
        }
        return toListingDTO.convert(listingRepository.findById(parseId).orElseThrow(()-> new NotFoundException("Record with id: "+ id +"not found!")));
    }


    public ResponseEntity<?> saveOrUpdate(ListingDTO listingDTO) {
        try{
            if(listingRepository.existsByListing(listingDTO.getListing())){
                return new ResponseEntity<>("There is already a listing with this name", HttpStatus.BAD_REQUEST);}
                else{
                    Listing listing=new Listing();
                if(listingDTO.getId()!=null){
                    listing.setId(listingDTO.getId());}
                listing.setListing(listingDTO.getListing());
                listingRepository.save(listing);
                return new ResponseEntity<>("Listing saved successfully", HttpStatus.OK);
            }
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }



    }
}

