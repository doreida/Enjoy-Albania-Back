package net.sparklab.AirBNBReservation.restController;

import lombok.RequiredArgsConstructor;
import net.sparklab.AirBNBReservation.dto.ChangePasswordDTO;
import net.sparklab.AirBNBReservation.dto.ProfileUpdateDTO;
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


    @PutMapping ("/update")
    public Object updateUser(@ModelAttribute ProfileUpdateDTO profileUpdateDTO){
        return userService.updateUser(profileUpdateDTO);
    }
    @PostMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @PathVariable("id") Long id){
        return userService.updatePassword(changePasswordDTO, id);
    }

    @GetMapping("/{id}")
    public ProfileUpdateDTO findUserById(@PathVariable String id){
        return userService.findById(id);
    }


}
