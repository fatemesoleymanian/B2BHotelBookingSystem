package com.example.B2BHotelBookingSystem.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE reservations SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "reservations")
public class Reservation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserve_gen")
    @SequenceGenerator(name = "reserve_gen", sequenceName = "reserve_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReserveStatus status;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "room_rate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rate> prices = new HashSet<>();

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime from;

    @Column(nullable = false)
    private LocalDateTime to;

    @Column(name = "guest_first_name", nullable = false, length = 50)
    private String guestFirstName;

    @Column(name = "guest_last_name", nullable = false, length = 50)
    private String guestLastName;

    @Column(name = "guest_phone_number", nullable = false, length = 20)
    private String guestPhoneNumber;

    @Column(length = 100)
    private String description;
}
