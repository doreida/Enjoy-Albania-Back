package net.sparklab.AirBNBReservation.converters;


import net.sparklab.AirBNBReservation.dto.ListingDTO;

import net.sparklab.AirBNBReservation.model.Listing;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListingDTOToListing implements Converter<ListingDTO, Listing> {


    @Override
    public Listing convert(ListingDTO source) {
        if (source != null) {
            Listing listing = new Listing();

            if (source.getId() != null) {
                listing.setId(source.getId());
            }
            listing.setListing(source.getListing());
            return listing;
        }
        return null;
    }
}