package za.co.simplitate.hotelbooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.simplitate.hotelbooking.entities.Booking;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingByUser(Long userId);

    Optional<Booking> findBookingByBookingReference(String bookingReference);

    boolean isRoomAvailable(Long roomId, String checkInDate, String checkOutDate);


}
