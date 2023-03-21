package net.sparklab.AirBNBReservation.services;


import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.RegistrationDTO;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class RegistrationService {


    private final UserRepository userRepository;
//    private final EncryptionService encryptionService;
    //TODO email service
    public ResponseEntity<?> registerUser(RegistrationDTO registrationDTO) {

        Users usertoSave=new Users();

        if(userRepository.existsByEmail(registrationDTO.getEmail())==Boolean.TRUE)
        {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(registrationDTO.getUsername())==Boolean.TRUE)
        {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);

        }

        else{
            usertoSave.setName(registrationDTO.getName());
            usertoSave.setSurname(registrationDTO.getSurname());
            usertoSave.setPhone(registrationDTO.getPhone());
            usertoSave.setEmail(registrationDTO.getEmail());
            usertoSave.setUsername(registrationDTO.getUsername());
            userRepository.save(usertoSave);
            return new ResponseEntity<>("Record saved sucesfully",HttpStatus.OK);
        }


    }

}
