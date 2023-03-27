package net.sparklab.AirBNBReservation.services;



import net.sparklab.AirBNBReservation.dto.ConfirmationRequest;
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

import static java.lang.Boolean.TRUE;



@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    private String primary_password="enjoyalbaniasparklab";
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public ResponseEntity<?> registerUser(RegistrationDTO registrationDTO) {

        Users usertoSave=new Users();


        if(userRepository.existsByEmail(registrationDTO.getEmail())== TRUE)
        {

            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(registrationDTO.getUsername())== TRUE)
        {
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
            usertoSave.setPassword(bCryptPasswordEncoder.encode(primary_password));
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


    public String savePassword(String token, ConfirmationRequest confirmationRequest) {

        Users userToUpdatePassword=userRepository.findUsersByConfirmationToken(token);

           if(userToUpdatePassword.getTokenConfirmationDate()!=null){
               return "user already confirmed";
        }


        LocalDateTime expiredAt=userToUpdatePassword.getTokenCreationDate().plusHours(24);
        if(expiredAt.isBefore(LocalDateTime.now())){
           return "token is expired";
            //TODO delete user after24h fromdb
        }
        userToUpdatePassword.setTokenConfirmationDate(LocalDateTime.now());
        String encodedUserPassword=bCryptPasswordEncoder.encode(confirmationRequest.getPassword());
        userToUpdatePassword.setPassword(encodedUserPassword);
        userToUpdatePassword.setEnabled(TRUE);
        userRepository.save(userToUpdatePassword);

        return "User password saved correctly";
    }







}
