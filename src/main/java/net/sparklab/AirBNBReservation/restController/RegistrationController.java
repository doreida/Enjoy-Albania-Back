package net.sparklab.AirBNBReservation.restController;

import net.sparklab.AirBNBReservation.dto.ConfirmationRequest;
import net.sparklab.AirBNBReservation.dto.RegistrationDTO;
import net.sparklab.AirBNBReservation.services.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/enjoyAlbania")
public class RegistrationController {

private final RegistrationService registrationService;


    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO) {

        return registrationService.registerUser(registrationDTO);


    }
    @PostMapping("savepassword/{token}")
    public String savePassword(@PathVariable("token") String token, @RequestBody ConfirmationRequest confirmationRequest) {

        return registrationService.savePassword(token, confirmationRequest);


    }

}
