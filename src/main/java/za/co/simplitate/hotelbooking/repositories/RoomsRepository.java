package za.co.simplitate.hotelbooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.simplitate.hotelbooking.entities.Room;
import za.co.simplitate.hotelbooking.enums.RoomType;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Room, Long> {

//    List<Room> findavailableRooms(LocalDate checkInDate, LocalDate checkOutDate, RoomType roomType);

//    List<Room> searchRooms(String searchParam);
}
