package au.id.res.lodging.lodging_reservation_service.service;

import au.id.res.lodging.lodging_reservation_service.data.entity.Guest;
import au.id.res.lodging.lodging_reservation_service.data.entity.Reservation;
import au.id.res.lodging.lodging_reservation_service.data.entity.Room;
import au.id.res.lodging.lodging_reservation_service.data.repository.GuestRepository;
import au.id.res.lodging.lodging_reservation_service.data.repository.ReservationRepository;
import au.id.res.lodging.lodging_reservation_service.data.repository.RoomRepository;
import au.id.res.lodging.lodging_reservation_service.service.model.RoomReservation;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*"Hey Spring, manage this class as a Bean, and its job is to hold business logic."*/
@Service
public class RoomReservationService {
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public RoomReservationService(GuestRepository guestRepository, RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(String reservationDate){
        Date date = null;
        if(StringUtils.isNotEmpty(reservationDate)){
            date = Date.valueOf(reservationDate);
        } else {
            date = new Date(new java.util.Date().getTime());
        }

        Map<Long, RoomReservation> roomReservations = new HashMap<>();
        List<Room> rooms = this.roomRepository.findAll();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservations.put(roomReservation.getRoomId(), roomReservation);
        });

        List<Reservation> reservations = this.reservationRepository.findAllByReservationDate(date);
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservations.get(reservation.getRoomId());
            roomReservation.setReservationId(reservation.getId());
            roomReservation.setReservationDate(reservation.getReservationDate().toString());
            this.guestRepository.findById(reservation.getGuestId())
                    .ifPresent(guest -> {
                        roomReservation.setGuestId(guest.getId());
                        roomReservation.setFirstName(guest.getFirstName());
                        roomReservation.setLastName(guest.getLastName());
                    })
            ;
        });

        return roomReservations.values().stream().toList();
    }
}
