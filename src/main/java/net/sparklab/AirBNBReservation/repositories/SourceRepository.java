package net.sparklab.AirBNBReservation.repositories;

import net.sparklab.AirBNBReservation.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source,Long> {


    Source findSourcesBySource(String source);

    boolean existsBySource(String source);
}
