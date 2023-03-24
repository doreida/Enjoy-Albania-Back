package net.sparklab.AirBNBReservation.services;



import net.sparklab.AirBNBReservation.dto.RegistrationDTO;
import net.sparklab.AirBNBReservation.model.Role;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;




@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public ResponseEntity<?> registerUser(RegistrationDTO registrationDTO) {

        Users usertoSave=new Users();

        if(userRepository.existsByEmail(registrationDTO.getEmail())==Boolean.TRUE)
        {//throw new IllegalStateException("email already taken");
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(registrationDTO.getUsername())==Boolean.TRUE)
        {  //throw new IllegalStateException("username already taken");
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);

        }
        else{
            usertoSave.setName(registrationDTO.getName());
            usertoSave.setSurname(registrationDTO.getSurname());
            usertoSave.setPhone(registrationDTO.getPhone());
            usertoSave.setEmail(registrationDTO.getEmail());
            usertoSave.setUsername(registrationDTO.getUsername());
            usertoSave.setConfirmationToken(generateConfirmationToken());
            usertoSave.setTokenCreationDate(LocalDateTime.now());
            usertoSave.setRole(Role.valueOf(registrationDTO.getRole()));


            userRepository.save(usertoSave);

           String link="http://localhost:3000/enjoyAlbania/registration/"+usertoSave.getConfirmationToken();
       emailService.send(registrationDTO.getEmail(),emailService.buildEmail(registrationDTO.getName(),link));
              return new ResponseEntity<>(usertoSave.getConfirmationToken(), HttpStatus.OK);

        }


    }

    private String generateConfirmationToken() {
        String confirmationToken = UUID.randomUUID().toString();
        return confirmationToken;
    }

}
