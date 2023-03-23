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


    public ResponseEntity<?> registerUser(RegistrationDTO registrationDTO) {
    return null;
    }
}
