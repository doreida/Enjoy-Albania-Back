package net.sparklab.AirBNBReservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDTO {
    private final String password;
    private final String newPassword;
}
