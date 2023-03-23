package net.sparklab.AirBNBReservation.services;


import lombok.RequiredArgsConstructor;
import net.sparklab.AirBNBReservation.converters.ProfileUpdateDTOToUser;
import net.sparklab.AirBNBReservation.dto.ProfileUpdateDTO;
import net.sparklab.AirBNBReservation.model.User;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final ProfileUpdateDTOToUser toUser;

    public Object updateUser(ProfileUpdateDTO profileUpdateDTO) {
      User user = userRepository.save(toUser.convert(profileUpdateDTO));
        Map<String,Object> response = new HashMap<>();
        response.put("photo",user.getPhoto());
        response.put("fileType",user.getFileType());
        return response;
    }
}
