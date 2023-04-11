package net.sparklab.AirBNBReservation.dto;

import lombok.Data;

@Data
public class APIResponse {
    private String accessToken;
    private String email;
    private String name;
    private String surname;
    private Long id;
    private String role;
    private byte[] profilePhoto;
    private String profilePhotoType;



}
