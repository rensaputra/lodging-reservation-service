package au.id.res.lodging.lodging_reservation_service.data.repository;

import au.id.res.lodging.lodging_reservation_service.data.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByFirstNameIgnoreCase(String name);
    Optional<Guest> findByLastNameIgnoreCase(String name);
    Optional<Guest> findByEmailAddressIgnoreCase(String email);
    Optional<Guest> findByAddressContaining(String address);
    Optional<Guest> findByCountryIgnoreCase(String country);
}
