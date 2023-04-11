package net.sparklab.AirBNBReservation.converters;

import net.sparklab.AirBNBReservation.dto.ListingDTO;

import net.sparklab.AirBNBReservation.dto.SourceDTO;
import net.sparklab.AirBNBReservation.model.Listing;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListingToListingDTO  implements Converter<Listing, ListingDTO> {


    @Override
    public ListingDTO convert(Listing source) {
        if (source != null) {
            ListingDTO listingDTO = new ListingDTO();
            listingDTO.setId(source.getId());
            listingDTO.setListing(source.getListing());
            return listingDTO;
        }
        return null;

    }
}
