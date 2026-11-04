package au.id.res.lodging.lodging_reservation_service.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

@Entity
@Table(name="reservations", schema="lodging")
@Getter
@Setter
@ToString(exclude = {"room", "guest"})

public class Reservation {
    @Id
    @Column(name="reservation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="guest_id", nullable=false)
    private Guest guest;

    @Column(name="res_date")
    private LocalDate resDate;
}
