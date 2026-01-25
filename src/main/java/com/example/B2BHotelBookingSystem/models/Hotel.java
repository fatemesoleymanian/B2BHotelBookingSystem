package com.example.B2BHotelBookingSystem.models;

import com.example.B2BHotelBookingSystem.config.exceptions.DynamicTextException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter @Setter
@Entity @SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE hotels SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "hotels")
public class Hotel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_gen")
    @SequenceGenerator(name = "hotel_gen", sequenceName = "hotel_seq",allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 250)
    private String address;

    @Column(nullable = false, length = 50, name = "city_name")
    private String cityName;

    @Column(nullable = false)
    @Check(constraints = "star between 1 and 5")
    private Integer star;

    @Column(length = 15)
    private String tel;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Room> rooms = new HashSet<>();it gets me error in creating table
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rate> rates = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public void addRoom(Room room) {
        if (room == null) {
            throw new DynamicTextException("Room cannot be null");
        }
        this.rooms.add(room);
        room.setHotel(this);
    }

    public void removeRoom(Room room){
        if (room == null){
            throw new DynamicTextException("Room cannot be null");
        }
        this.rooms.remove(room);
        room.setHotel(null);
    }

}
