package au.id.res.lodging.lodging_reservation_service.data.repository;

import au.id.res.lodging.lodging_reservation_service.data.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByReservationId(Long id);
    Optional<Reservation> findByRoomId(Long roomId);
    Optional<Reservation> findByGuestId(Long guestId);
    Optional<Reservation> findByResDate(LocalDate date);
}
