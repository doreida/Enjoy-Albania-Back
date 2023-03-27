package net.sparklab.AirBNBReservation.repositories;

import net.sparklab.AirBNBReservation.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {


    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<Users>findUsersByUsername(String username);
    Optional<Users> findUsersByEmail(String email);

    @Query(value="select * from users u where u.email=:email AND u.is_enabled=1",nativeQuery = true)
    Optional<Users> findUsersByEmailEnabled(String email);

    Users findUsersByConfirmationToken(String token);




//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE users SET is_enabled =TRUE WHERE email=:email",nativeQuery = true)
//    void enableUser(@Param("email") String email);



}
