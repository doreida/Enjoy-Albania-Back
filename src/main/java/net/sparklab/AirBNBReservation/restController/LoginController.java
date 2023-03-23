package net.sparklab.AirBNBReservation.restController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import net.sparklab.AirBNBReservation.dto.LoginDTO;
import net.sparklab.AirBNBReservation.services.LogInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enjoyAlbania/auth")
@AllArgsConstructor
public class LoginController {

    private final LogInService logInService;
    @PostMapping("/login")
    public Object authenticateUser(@RequestBody LoginDTO loginDto) {
        Object apiResponse = logInService.login(loginDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }



}
