package net.sparklab.AirBNBReservation.dto;

import lombok.Data;

@Data
public class FilterDTO {

    String sortBy;
    String sortDir;
    String start;
    String end;
    String earningMin;
    String earningMax;
    int pageSize;
    int pageNo;
    Boolean createdDate = false;
    Boolean bookedDate = false;
    Boolean startDate = false;
    Boolean endDate = false;
    Boolean startToEnd = false;

    private String status;
    private String guestName;
    private String contact;
    private String listing;
    private String confirmationCode;


}
