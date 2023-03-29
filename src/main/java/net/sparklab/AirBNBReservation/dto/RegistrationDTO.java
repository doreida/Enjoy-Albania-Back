package net.sparklab.AirBNBReservation.dto;


import lombok.Data;
import org.springframework.stereotype.Component;


@Data
public class RegistrationDTO {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String username;
    private Boolean isEnabled=false;


}
