package net.sparklab.AirBNBReservation.repositories;

import net.sparklab.AirBNBReservation.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Users>findUsersByUsername(String username);
    Optional<Users> findUsersByEmail(String email);
}
