package net.sparklab.AirBNBReservation.restController;


import lombok.RequiredArgsConstructor;
import net.sparklab.AirBNBReservation.dto.ResetpasswordDTO;
import net.sparklab.AirBNBReservation.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("enjoyAlbania/user")
@RequiredArgsConstructor
public class UserController {

private final UserService userService;
    @PostMapping("/forgotPassword/{email}")
    public ResponseEntity forgotPassword(@PathVariable String email) throws MessagingException {
        return userService.forgotPassword(email);
    }



    @PutMapping("/resetPassword/{token}")
    public String resetPassword(@PathVariable(value = "token") String token, @RequestBody ResetpasswordDTO resetpasswordDTO){
        return userService.resetPassword(token,resetpasswordDTO);

    }






}
