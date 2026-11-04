package au.id.res.lodging.lodging_reservation_service.data.repository;

import au.id.res.lodging.lodging_reservation_service.data.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByReservationDate(Date date);
}
