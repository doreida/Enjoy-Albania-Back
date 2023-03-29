package net.sparklab.AirBNBReservation.restController;

import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.RegistrationDTO;
import net.sparklab.AirBNBReservation.services.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/enjoyalbania/registr")
public class RegistrationController {

private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO) {

        return registrationService.registerUser(registrationDTO);


    }







}
