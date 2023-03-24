package net.sparklab.AirBNBReservation.services;

import net.sparklab.AirBNBReservation.converters.ProfileUpdateDTOToUser;
import net.sparklab.AirBNBReservation.dto.ProfileUpdateDTO;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    private final ProfileUpdateDTOToUser toUser;

    public UserService(UserRepository userRepository, ProfileUpdateDTOToUser toUser) {
        this.userRepository = userRepository;
        this.toUser = toUser;
    }

    public Optional<Users> findById(Long id){
        return userRepository.findById(id);
    }
    public ResponseEntity<?> updateUser(ProfileUpdateDTO profileUpdateDTO) {
        try {
            Users user = userRepository.save(toUser.convert(profileUpdateDTO));
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("User details are not updated",HttpStatus.BAD_REQUEST);
        }


    }
}
