package net.sparklab.AirBNBReservation.converters;

import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import net.sparklab.AirBNBReservation.utils.StatusUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GuestDTOtoGuest implements Converter<GuestDTO, net.sparklab.AirBNBReservation.model.Guest> {
    public final GuestRepository guestRepository;

    public GuestDTOtoGuest(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public net.sparklab.AirBNBReservation.model.Guest convert(GuestDTO source) {
        if(source!=null){
            net.sparklab.AirBNBReservation.model.Guest guest = new net.sparklab.AirBNBReservation.model.Guest();
            if(source.getId()!=null){
                guest.setId(source.getId());
            }
            guest.setFirstName(source.getFirstname()!=null ? source.getFirstname():null);
            guest.setLastName(source.getLastname()!= null ? source.getLastname():null);
            guest.setStatus(source.getStatus()!= null ? StatusUtils.getStatus(source.getStatus()):null);
            guest.setContact(source.getContact()!=null ? source.getContact() : null);
            return guest;
        }
        return null;
    }
}
