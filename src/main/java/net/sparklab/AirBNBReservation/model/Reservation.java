package net.sparklab.AirBNBReservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation extends BaseEntity{
    private LocalDateTime createdDate;
    //    private long lastModifiedDate
    private String confirmationCode;
    private int noAdults;
    private int noChildren;
    private int noInfants;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate bookedDate;
    private Long anticipation;
    private int noNights;
    private Currency currency;
    private BigDecimal earning;

    @ManyToOne
    @JoinColumn(name = "guest_id",referencedColumnName = "id", nullable = false)
    private Guest guest;



    @ManyToOne
    @JoinColumn(name = "listing_id",referencedColumnName = "id", nullable = false)
    private Listing listing;



    @ManyToOne
    @JoinColumn(name = "source_id",referencedColumnName = "id",nullable = true)
    private Source source;



}