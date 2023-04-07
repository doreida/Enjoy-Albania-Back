package net.sparklab.AirBNBReservation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Source extends BaseEntity{

    private String source;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "source")
    List<Reservation> reservations;

}
