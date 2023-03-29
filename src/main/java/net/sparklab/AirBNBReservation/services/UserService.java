

import net.sparklab.AirBNBReservation.dto.ResetpasswordDTO;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
    @Autowired
    private final UserRepository userRepository;
    private final UserToProfileUpdate toProfileUpdate;
    private final ProfileUpdateDTOToUser toUser;

    public UserService(UserRepository userRepository, UserToProfileUpdate toProfileUpdate, ProfileUpdateDTOToUser toUser) {
        this.userRepository = userRepository;
        this.toProfileUpdate = toProfileUpdate;
        this.toUser = toUser;
    }

    public ProfileUpdateDTO findById(String id) {
        Long parseId;
        try {  parseId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("User id: \"" + id + "\" can't be parsed!");
        }        return toProfileUpdate.convert(userRepository.findById(parseId).orElseThrow(() -> new NotFoundException("Record with id: " + id + " notfound!")));
    }

