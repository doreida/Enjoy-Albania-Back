package net.sparklab.AirBNBReservation.services;

import net.sparklab.AirBNBReservation.converters.ProfileUpdateDTOToUser;
import net.sparklab.AirBNBReservation.converters.UserToProfileUpdate;
import net.sparklab.AirBNBReservation.dto.ChangePasswordDTO;
import net.sparklab.AirBNBReservation.dto.ProfileUpdateDTO;
import net.sparklab.AirBNBReservation.dto.ResetpasswordDTO;
import net.sparklab.AirBNBReservation.exceptions.NotFoundException;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Boolean.TRUE;

@Service
public class UserService{
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 60;

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserToProfileUpdate toProfileUpdate;
    private final ProfileUpdateDTOToUser toUser;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CustomUserDetailsService customUserDetailsService, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder, UserToProfileUpdate toProfileUpdate, ProfileUpdateDTOToUser toUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder= bCryptPasswordEncoder;
        this.toProfileUpdate = toProfileUpdate;
        this.toUser = toUser;
        this.passwordEncoder = passwordEncoder;
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
            return new ResponseEntity<>("No user exists with this email ", HttpStatus.BAD_REQUEST);
        }
        Users user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate_forgetpassword(LocalDateTime.now());
        user = userRepository.save(user);
        String link = "http://192.168.10.12:3000/enjoyAlbania/resetPassword/" + user.getToken();
        if(customUserDetailsService.exists(email)){
            emailService.send(user.getEmail(),emailService.buildResetEmail(user.getName(),link));
        }
        return new ResponseEntity<>(user.getToken(),HttpStatus.OK);
    }

    public ResponseEntity<?> resetPassword(String uuid, ResetpasswordDTO resetpasswordDTO){
        Optional<Users> optionalUser = Optional.ofNullable(userRepository.findByToken(uuid));
        if (!optionalUser.isPresent()){
            return  new ResponseEntity<>( "The  redirection link is invalid ",HttpStatus.NOT_ACCEPTABLE);
        }
        LocalDateTime tokenCreationDate = optionalUser.get().getTokenCreationDate_forgetpassword();
        if(tokenIsExpired(tokenCreationDate)){
            return  new ResponseEntity<>("The link has expired. Please complete again the forgot password form.",HttpStatus.NOT_ACCEPTABLE);
        }
        Users user = optionalUser.get();
        user.setPassword(bCryptPasswordEncoder.encode(resetpasswordDTO.getPassword()));
        userRepository.save(user);
        user.setToken(null);
        userRepository.save(user);
        return  new ResponseEntity<>("Your password was successfully changed !",HttpStatus.OK);
    }



    public ProfileUpdateDTO findById(String id){
            Long parseId;
            try {
                parseId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("User id: \"" + id + "\" can't be parsed!");
            }
            return toProfileUpdate.convert(userRepository.findById(parseId).orElseThrow(() -> new NotFoundException("Record with id: " + id + " notfound!")));
        }


    @Transactional
    public ResponseEntity<?> updateUser(ProfileUpdateDTO profileUpdateDTO) {
        try {

            Users user = userRepository.save(toUser.convert(profileUpdateDTO));
            return new ResponseEntity<>("User details are updated sucesfully",HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("User details are not updated",HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> updatePassword(ChangePasswordDTO changePasswordDTO, Long userId){
        Users currentUser = userRepository.findById(userId).get();
        if(Objects.nonNull(changePasswordDTO.getPassword())&& !"".equalsIgnoreCase(changePasswordDTO.getPassword())){

        }
        if(passwordEncoder.matches(changePasswordDTO.getPassword(), currentUser.getPassword())){
            BCryptPasswordEncoder passwordEncoder1 = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder1.encode(changePasswordDTO.getNewPassword());
            currentUser.setPassword(encodePassword);
            userRepository.save(currentUser);
            return new ResponseEntity<>("Your password was changed successfully!", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Your password is wrong! Please put your correct password",HttpStatus.BAD_REQUEST);
        }
    }



    }