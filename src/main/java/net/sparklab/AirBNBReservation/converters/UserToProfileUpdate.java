package net.sparklab.AirBNBReservation.converters;


import net.sparklab.AirBNBReservation.dto.ProfileUpdateDTO;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToProfileUpdate implements Converter<Users,ProfileUpdateDTO> {

    private final UserRepository userRepository;


    public UserToProfileUpdate(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


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
