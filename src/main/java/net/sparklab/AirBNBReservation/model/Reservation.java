package net.sparklab.AirBNBReservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation extends BaseEntity{
    private LocalDate createdDate;
    //    private long lastModifiedDate
    private String confirmationCode;
    private int noAdults;
    private int noChildren;
    private int noInfants;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate bookedDate;
    private int noNights;
    private String listing;
    private Currency currency;
    private BigDecimal earning;

    @ManyToOne
    @JoinColumn(name = "guest_id",referencedColumnName = "id", nullable = false)
    private Guest guest;

}