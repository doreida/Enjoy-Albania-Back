package net.sparklab.AirBNBReservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateDTO {

    Long id;
    String name;
    String surname;
    String email;
    String phone;
    MultipartFile photo;


}
