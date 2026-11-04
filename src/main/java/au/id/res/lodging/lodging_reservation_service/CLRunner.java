package au.id.res.lodging.lodging_reservation_service;

import au.id.res.lodging.lodging_reservation_service.data.entity.Room;
import au.id.res.lodging.lodging_reservation_service.data.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CLRunner implements CommandLineRunner {

    private final RoomRepository roomRepository;

    public CLRunner(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Room> rooms = this.roomRepository.findAll();
        Optional<Room> room = this.roomRepository.findByRoomNumberIgnoreCase("p1");
        room.ifPresent(System.out::println);
        rooms.forEach(System.out::println);
    }
}
