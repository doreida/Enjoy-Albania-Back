package net.sparklab.AirBNBReservation.converters;

import net.sparklab.AirBNBReservation.dto.GuestDTO;
import net.sparklab.AirBNBReservation.model.Guest;
import net.sparklab.AirBNBReservation.repositories.GuestRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GuestDTOtoGuest implements Converter<GuestDTO, Guest> {
    public final GuestRepository guestRepository;

    public GuestDTOtoGuest(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public Guest convert(GuestDTO source) {
        if(source!=null){
            Guest guest = new Guest();
            if(source.getId()!=null){
                guest.setId(source.getId());
            }
            guest.setFirstname(source.getFirstname()!=null ? source.getFirstname():guestRepository.findById(source.getId()).get().getFirstname());
            guest.setLastname(source.getLastname()!= null ? source.getLastname():guestRepository.findById(source.getId()).get().getLastname());
            guest.setStatus(source.getStatus()!= null ? source.getStatus():guestRepository.findById(source.getId()).get().getStatus());
            guest.setContact(source.getContact()!=null ? source.getContact() : guestRepository.findById(source.getId()).get().getContact());
            return guest;
        }
        return null;
    }
}
