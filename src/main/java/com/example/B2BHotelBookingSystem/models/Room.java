package com.example.B2BHotelBookingSystem.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity @SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE rooms SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "rooms",
        indexes = {
                @Index(name = "idx_room_hotel", columnList = "hotel_id"),
                @Index(name = "idx_room_active", columnList = "active"),
                @Index(name = "idx_room_type", columnList = "room_type")
        })
public class Room extends BaseEntity{
    @Id
    //appropriate for postgres
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_gen")
    //@SequenceGenerator(name = "room_gen", sequenceName = "room_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, name = "main_capacity")
    @Check(constraints = "main_capacity between 1 and 15")
    private Integer mainCapacity = 0;

    @Column(nullable = false, name = "child_capacity")
    @Check(constraints = "child_capacity between 1 and 15")
    private Integer childCapacity = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "room_type")
    private RoomType roomType;

    @Column(nullable = false)
    private BigDecimal price;//for those without contract

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Availability> availabilities = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<ReserveItem> items = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Room)) return false;

        Room other = (Room) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
