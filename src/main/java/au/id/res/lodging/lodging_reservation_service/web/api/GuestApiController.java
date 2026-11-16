package au.id.res.lodging.lodging_reservation_service.web.api;

import au.id.res.lodging.lodging_reservation_service.data.entity.Guest;
import au.id.res.lodging.lodging_reservation_service.data.repository.GuestRepository;
import au.id.res.lodging.lodging_reservation_service.web.exception.BadRequestException;
import au.id.res.lodging.lodging_reservation_service.web.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/guests")
public class GuestApiController {
    private final GuestRepository guestRepository;

    public GuestApiController(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @GetMapping
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Guest createGuest(@RequestBody Guest guest) {
        return this.guestRepository.save(guest);
    }

    @GetMapping("/{id}")
    public Guest getGuestById(@PathVariable("id") long id) {
        Optional<Guest> guest = this.guestRepository.findById(id);
        if(guest.isEmpty()) {
            throw new NotFoundException("Guest with id " + id + " not found");
        }
        return guest.get();
    }

    @PutMapping("/{id}")
    public Guest updateGuest(@PathVariable("id") long id, @RequestBody Guest guest){
        if(id != guest.getId()) {
            throw new BadRequestException("id on path doesn't match body");
        }
        return this.guestRepository.save(guest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteGuestById(@PathVariable("id") long id){
        this.guestRepository.deleteById(id);
    }
}