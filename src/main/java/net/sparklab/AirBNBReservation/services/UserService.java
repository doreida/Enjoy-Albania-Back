package net.sparklab.AirBNBReservation.services;

import net.sparklab.AirBNBReservation.converters.ProfileUpdateDTOToUser;
import net.sparklab.AirBNBReservation.converters.UserToProfileUpdate;
import net.sparklab.AirBNBReservation.dto.ProfileUpdateDTO;
import net.sparklab.AirBNBReservation.exceptions.NotFoundException;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final UserToProfileUpdate toProfileUpdate;
    private final ProfileUpdateDTOToUser toUser;

    public UserService(UserRepository userRepository, UserToProfileUpdate toProfileUpdate, ProfileUpdateDTOToUser toUser) {
        this.userRepository = userRepository;
        this.toProfileUpdate = toProfileUpdate;
        this.toUser = toUser;
    }

    public ProfileUpdateDTO findById(String id) {
        Long parseId;
        try {            parseId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Photo id: \"" + id + "\" can't be parsed!");
        }        return toProfileUpdate.convert(userRepository.findById(parseId).orElseThrow(() -> new NotFoundException("Record with id: " + id + " notfound!")));
    }







    @Transactional
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
