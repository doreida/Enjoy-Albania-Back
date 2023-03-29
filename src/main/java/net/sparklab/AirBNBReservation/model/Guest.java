package net.sparklab.AirBNBReservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guest extends BaseEntity{
    private String firstName;
    private String lastName;
    private String contact;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "guest")

    List<Reservation> reservations;
}
