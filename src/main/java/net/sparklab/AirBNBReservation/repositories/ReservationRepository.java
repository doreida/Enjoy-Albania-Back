package net.sparklab.AirBNBReservation.repositories;

import net.sparklab.AirBNBReservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
