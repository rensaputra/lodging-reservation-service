package au.id.res.lodging.lodging_reservation_service;

import au.id.res.lodging.lodging_reservation_service.data.entity.Guest;
import au.id.res.lodging.lodging_reservation_service.data.entity.Reservation;
import au.id.res.lodging.lodging_reservation_service.data.entity.Room;
import au.id.res.lodging.lodging_reservation_service.data.repository.GuestRepository;
import au.id.res.lodging.lodging_reservation_service.data.repository.ReservationRepository;
import au.id.res.lodging.lodging_reservation_service.data.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CLRunner implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;

    public CLRunner(RoomRepository roomRepository, ReservationRepository reservationRepository, GuestRepository guestRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository =  reservationRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("*** GUESTS ***");
        List<Guest> guests = this.guestRepository.findAll();
        guests.forEach(System.out::println);
        System.out.println(" *** ROOMS ***");
        List<Room> rooms = this.roomRepository.findAll();
        rooms.forEach(System.out::println);
        System.out.println(" *** RESERVATIONS ***");
        List<Reservation> reservations = this.reservationRepository.findAll();
        reservations.forEach(System.out::println);
    }
}
