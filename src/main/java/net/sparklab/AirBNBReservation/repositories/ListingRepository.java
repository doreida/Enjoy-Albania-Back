package net.sparklab.AirBNBReservation.repositories;

import net.sparklab.AirBNBReservation.model.Listing;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {


 Listing findByListing(String Listing);


 boolean existsByListing(String listing);

List<Listing> findAllByListingContaining(String listing);



}
