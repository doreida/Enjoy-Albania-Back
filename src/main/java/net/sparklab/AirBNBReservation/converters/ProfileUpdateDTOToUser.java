package net.sparklab.AirBNBReservation.converters;

import lombok.SneakyThrows;
import net.sparklab.AirBNBReservation.dto.ProfileUpdateDTO;
import net.sparklab.AirBNBReservation.exceptions.NotFoundException;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class ProfileUpdateDTOToUser implements Converter<ProfileUpdateDTO, Users> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileUpdateDTOToUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @SneakyThrows
    @Override
    public Users convert(ProfileUpdateDTO source) {
        if (source.getId() != null) {
            Users user = userRepository.findById(source.getId()).orElseThrow(() ->
                    new NotFoundException("User not found"));

            user.setId(source.getId());

            if (source.getName() != null)
                user.setName(source.getName());

            if (source.getSurname() != null)
                user.setSurname(source.getSurname());

            if (source.getEmail() != null)
                user.setEmail(source.getEmail());

            if (source.getPhone() != null)
                user.setPhone(source.getPhone());

            if (source.getPhoto() == null & source.getId() != null) {
                user.setPhoto(user.getPhoto());
                user.setFileName(user.getFileName());
                user.setFileType(user.getFileType());
            } else {
                user.setPhoto(source.getPhoto().getBytes());
                user.setFileName(source.getPhoto().getOriginalFilename());
                user.setFileType(source.getPhoto().getContentType());
            }

            return user;
        }

        return null;
    }
}

