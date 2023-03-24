package net.sparklab.AirBNBReservation.repositories;

import net.sparklab.AirBNBReservation.model.Reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByGuestId(Long id);

    Boolean existsByConfirmationCode(String confirmationCode);

    Page<Reservation> findAll(Pageable pageable);


}
