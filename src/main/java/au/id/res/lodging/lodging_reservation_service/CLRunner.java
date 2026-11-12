package au.id.res.lodging.lodging_reservation_service;

import au.id.res.lodging.lodging_reservation_service.data.entity.Guest;
import au.id.res.lodging.lodging_reservation_service.data.entity.Reservation;
import au.id.res.lodging.lodging_reservation_service.data.entity.Room;
import au.id.res.lodging.lodging_reservation_service.data.repository.GuestRepository;
import au.id.res.lodging.lodging_reservation_service.data.repository.ReservationRepository;
import au.id.res.lodging.lodging_reservation_service.data.repository.RoomRepository;
import au.id.res.lodging.lodging_reservation_service.service.RoomReservationService;
import au.id.res.lodging.lodging_reservation_service.service.model.RoomReservation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/* Hey Spring, manage this class as a Bean—just instantiate it, wire its dependencies, and keep it in the container.
No special architectural role implied.*/

@Component
public class CLRunner implements CommandLineRunner {

    private final RoomReservationService roomReservationService;

    public CLRunner(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<RoomReservation> reservations = this.roomReservationService.getRoomReservationsForDate("2023-08-28");
        reservations.forEach(System.out::println);
    }
}
