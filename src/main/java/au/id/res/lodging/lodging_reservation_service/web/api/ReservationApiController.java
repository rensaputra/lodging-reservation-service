package au.id.res.lodging.lodging_reservation_service.web.api;

import au.id.res.lodging.lodging_reservation_service.data.entity.Reservation;
import au.id.res.lodging.lodging_reservation_service.data.repository.ReservationRepository;
import au.id.res.lodging.lodging_reservation_service.web.exception.BadRequestException;
import au.id.res.lodging.lodging_reservation_service.web.exception.NotFoundException;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.DateUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationApiController {
    private final ReservationRepository reservationRepository;

    public ReservationApiController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<Reservation> getAllReservations(@RequestParam(value="date", required=false) String dateString) {
        if(StringUtils.isNotBlank(dateString)) {
            Date date = Date.valueOf(dateString);
            return this.reservationRepository.findAllByReservationDate(date);
        }
        return this.reservationRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable long id) {
        Optional<Reservation> reservation = this.reservationRepository.findById(id);
        if(reservation.isEmpty()){
            throw new NotFoundException("Reservation with if " + id + " not found");
        }
        return reservation.get();
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable("id") long id, @RequestBody Reservation reservation) {
        if(id != reservation.getId()){
            throw new BadRequestException("id on path doesnt match the body");
        }
        return this.reservationRepository.save(reservation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteReservationById(@PathVariable long id) {
        this.reservationRepository.deleteById(id);
    }
}
