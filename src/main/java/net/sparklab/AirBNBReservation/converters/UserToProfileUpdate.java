package net.sparklab.AirBNBReservation.converters;


import net.sparklab.AirBNBReservation.dto.ProfileUpdateDTO;
import net.sparklab.AirBNBReservation.model.Users;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToProfileUpdate implements Converter<Users,ProfileUpdateDTO> {

    @Override
    public ProfileUpdateDTO convert(Users source) {
        if (source!=null){
            ProfileUpdateDTO profileUpdateDTO = new ProfileUpdateDTO();
            profileUpdateDTO.setId(source.getId());
            profileUpdateDTO.setName(source.getName());
            profileUpdateDTO.setSurname(source.getSurname());
            profileUpdateDTO.setEmail(source.getEmail());
            profileUpdateDTO.setPhone(source.getPhone());
            profileUpdateDTO.setPhotobyte(source.getPhoto());
            profileUpdateDTO.setFileName(source.getFileName());
            profileUpdateDTO.setFileType(source.getFileType());
            return profileUpdateDTO;
        }
        return null;
    }
}
