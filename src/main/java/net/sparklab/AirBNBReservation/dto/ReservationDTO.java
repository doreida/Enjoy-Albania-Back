package net.sparklab.AirBNBReservation.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;


@Data
public class ReservationDTO {

    private Long id;
    private String createdDate;
    //    private long lastModifiedDate
    private GuestDTO guest;
    @CsvBindByName(column = "Confirmation code")
    private String confirmCode;
    @CsvBindByName(column = "Status")
    private String status;
    @CsvBindByName(column = "Guest name")
    private String guestName;
    @CsvBindByName(column = "Contact")
    private String contact;
    @CsvBindByName(column = "# of adults")
    private int nrAdults;
    @CsvBindByName(column = "# of children")
    private int nrChildren;
    @CsvBindByName(column = "# of infants")
    private int nrInfants;
    @CsvBindByName(column = "Start date")
    private String startDate;
    @CsvBindByName(column = "End date")
    private String endDate;
    @CsvBindByName(column = "# of nights")
    private int nrNights;
    @CsvBindByName(column = "Booked")
    private String bookedDate;
    @CsvBindByName(column = "Listing")
    private String listing;
    @CsvBindByName(column = "Earnings")
    private String earnings;


}
