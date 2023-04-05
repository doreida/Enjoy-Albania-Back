package net.sparklab.AirBNBReservation.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class ConfirmationRequest {
    private String confirmationToken;
    private String password;
    private LocalDateTime confirmationDate=LocalDateTime.now();


}
