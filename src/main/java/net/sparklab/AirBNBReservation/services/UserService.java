package net.sparklab.AirBNBReservation.services;


import net.sparklab.AirBNBReservation.dto.ResetpasswordDTO;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 60;

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, CustomUserDetailsService customUserDetailsService, EmailService emailService,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder= bCryptPasswordEncoder;
    }


    private String generateToken(){
        UUID uuid = UUID.randomUUID();
        return String.valueOf(uuid);
    }
    private boolean tokenIsExpired(final LocalDateTime tokenCreationDate_forgetpassword){
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate_forgetpassword,now);
        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }



    public ResponseEntity forgotPassword(String email) throws MessagingException{
        Optional<Users> userOptional = userRepository.findUsersByEmail(email);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>("Invalid email! ", HttpStatus.BAD_REQUEST);
        }
        Users user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate_forgetpassword(LocalDateTime.now());
        user = userRepository.save(user);

        if(customUserDetailsService.exists(email)){
            emailService.sendMessage(user,email);
        }
        return new ResponseEntity<>(user.getToken(),HttpStatus.OK);
    }



    public String resetPassword(String uuid, ResetpasswordDTO resetpasswordDTO){
        Optional<Users> optionalUser = Optional.ofNullable(userRepository.findByToken(uuid));
        if (!optionalUser.isPresent()){
            return "Invalid redirection link";
        }
        LocalDateTime tokenCreationDate = optionalUser.get().getTokenCreationDate_forgetpassword();
        if(tokenIsExpired(tokenCreationDate)){
            return "Token is expired";
        }
        Users user = optionalUser.get();
        user.setPassword(bCryptPasswordEncoder.encode(resetpasswordDTO.getPassword()));
        userRepository.save(user);
        user.setToken(null);
        userRepository.save(user);
        return "Your password was successfully changed!";
    }


}
