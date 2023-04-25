package net.sparklab.AirBNBReservation.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileUpdateDTO {
    Long id;
    String name;
    String surname;
    String email;
    String phone;
    MultipartFile photo;
    String fileType;
    String fileName;
    byte[] photobyte;
}
