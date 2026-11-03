package au.id.res.lodging.lodging_reservation_service.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="rooms", schema="lodging")
@Data
@ToString
public class Room {
    @Id
    @Column(name="room_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="room_number")
    private String roomNumber;

    @Column(name="bed_info")
    private String bedInfo;
}