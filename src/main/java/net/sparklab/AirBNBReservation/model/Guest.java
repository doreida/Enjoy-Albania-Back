package net.sparklab.AirBNBReservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guest extends BaseEntity{
    private String firstname;
    private String lastname;
    private String contact;
    private Status status;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "guest")

    List<Reservation> reservations;
}
