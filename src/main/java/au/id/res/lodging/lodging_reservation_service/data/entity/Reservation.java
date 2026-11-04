package au.id.res.lodging.lodging_reservation_service.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name="reservations", schema="lodging")
@Data
@ToString

public class Reservation {
    @Id
    @Column(name="reservation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="room_id")
    private long roomId;

    @Column(name="guest_id")
    private long guestId;

    @Column(name="res_date")
    private Date reservationDate;
}
