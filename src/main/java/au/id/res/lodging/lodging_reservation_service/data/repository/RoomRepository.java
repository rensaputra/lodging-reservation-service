package au.id.res.lodging.lodging_reservation_service.data.repository;

import au.id.res.lodging.lodging_reservation_service.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumberIgnoreCase(String roomNumber);
    Optional<Room> findByNameIgnoreCase(String roomName);
    Optional<Room> findByBedInfoIgnoreCase(String bedInfo);
}
