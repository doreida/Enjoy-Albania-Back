package net.sparklab.AirBNBReservation.services;


import net.sparklab.AirBNBReservation.dto.APIResponse;
import net.sparklab.AirBNBReservation.dto.LoginDTO;
import net.sparklab.AirBNBReservation.model.Users;
import net.sparklab.AirBNBReservation.repositories.UserRepository;
import net.sparklab.AirBNBReservation.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service

public class LogInService {


    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;


    private final AuthenticationProvider authenticationManager;

    public LogInService(UserRepository userRepository, JwtUtils jwtUtils, AuthenticationProvider authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public Object login(LoginDTO loginRequestDTO) {

        APIResponse apiResponse = new APIResponse();
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateAccessToken(userRepository.findUsersByEmailEnabled(loginRequestDTO.getEmail()).get());
            //apiResponse.setAccessToken(token);
            apiResponse= setUserData(userRepository.findUsersByEmailEnabled(loginRequestDTO.getEmail()).get(),token);

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (BadCredentialsException badCredentialsException) {

            return "Bad credentials! ";

        }
        catch (NoSuchElementException recordException) {

            return "This user with this credentials doesn't exist";

        }
    }


    public APIResponse setUserData(Users user,String token) {
        APIResponse apiResponse=new APIResponse();
        apiResponse.setId(user.getId());
        apiResponse.setAccessToken(token);
        apiResponse.setName(user.getName());
        apiResponse.setSurname(user.getSurname());
        apiResponse.setEmail(user.getEmail());
        apiResponse.setProfilePhoto(user.getPhoto());
        apiResponse.setRole(user.getRole().name());
        apiResponse.setProfilePhotoType(user.getFileType());
        return apiResponse;
    }
}
