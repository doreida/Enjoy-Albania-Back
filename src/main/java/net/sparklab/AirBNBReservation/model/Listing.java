package net.sparklab.AirBNBReservation.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "listings")
public class Listing  extends BaseEntity {


    @Column(name="listing",nullable = false)
    private String listing;


    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "listing")
    private Set<Reservation> reservation;

}
