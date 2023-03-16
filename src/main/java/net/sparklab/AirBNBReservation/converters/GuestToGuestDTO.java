package net.sparklab.AirBNBReservation.converters;

import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.model.Guest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GuestToGuestDTO implements Converter<Guest, GuestDTO> {

    @Override
    public GuestDTO convert(Guest source) {
        if (source!=null){
            GuestDTO guestDTO = new GuestDTO();
            guestDTO.setId(source.getId());
            guestDTO.setFirstname(source.getFirstname());
            guestDTO.setLastname(source.getLastname());
            guestDTO.setStatus(source.getStatus());
            guestDTO.setContact(source.getContact());
            return guestDTO;
        }
    return null;
    }
}
