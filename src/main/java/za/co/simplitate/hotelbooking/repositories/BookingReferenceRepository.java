package za.co.simplitate.hotelbooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.simplitate.hotelbooking.entities.BookingReference;

import java.util.Optional;

public interface BookingReferenceRepository extends JpaRepository<BookingReference, Long> {

    Optional<BookingReference> findBookingReferenceByReferenceNumber(String refNumber);
}
