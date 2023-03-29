package net.sparklab.AirBNBReservation.repositories;

import net.sparklab.AirBNBReservation.model.Reservation;

import net.sparklab.AirBNBReservation.model.ReservationSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long>, JpaSpecificationExecutor<Reservation> {
    List<Reservation> findByGuestId(Long id);

    Boolean existsByConfirmationCode(String confirmationCode);

    Page<Reservation> findAll(Pageable pageable);

//    List<Reservation> findByBookedDateBetweenAndSpecification(LocalDate startDate, LocalDate endDate, ReservationSpecification spec );
//
//    List<Reservation> findByStartDateBetweenAndSpecification(LocalDate startDate, LocalDate endDate,ReservationSpecification spec);
//    List<Reservation> findByEndDateBetweenAndSpecification(LocalDate startDate, LocalDate endDate,ReservationSpecification spec);
//    List<Reservation> findByCreatedDateBetweenAndSpecification(LocalDate startDate, LocalDate endDate,ReservationSpecification spec);

//    List<Reservation> findBySpecification(ReservationSpecification specification);




}
