package net.sparklab.AirBNBReservation.repositories;

import net.sparklab.AirBNBReservation.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
