package net.sparklab.AirBNBReservation.restController;

import lombok.RequiredArgsConstructor;
import net.sparklab.AirBNBReservation.dto.ProfileUpdateDTO;
import net.sparklab.AirBNBReservation.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("enjoyAlbania/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping ("/update")
    public Object updateUser(@ModelAttribute ProfileUpdateDTO profileUpdateDTO){
        return userService.updateUser(profileUpdateDTO);
    }

    @GetMapping("/{id}")
    public ProfileUpdateDTO findUserById(@PathVariable String id){
        return userService.findById(id);
    }

}
