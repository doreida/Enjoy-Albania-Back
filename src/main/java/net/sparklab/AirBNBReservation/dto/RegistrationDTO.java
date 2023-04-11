package net.sparklab.AirBNBReservation.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Data
@Getter
@Setter
public class RegistrationDTO {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String role;
    private Boolean isEnabled=false;

}
