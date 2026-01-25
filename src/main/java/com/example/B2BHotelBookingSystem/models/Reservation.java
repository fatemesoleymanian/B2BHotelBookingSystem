package com.example.B2BHotelBookingSystem.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE reservations SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "reservations",
        indexes = {
                @Index(name = "idx_res_hotel", columnList = "hotel_id"),
                @Index(name = "idx_res_agency", columnList = "agency_id"),
                @Index(name = "idx_res_status", columnList = "status")
        })
public class Reservation extends BaseEntity{
    @Id
    //appropriate for postgres
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_gen")
    //@SequenceGenerator(name = "reservation_gen", sequenceName = "reservation_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Set<ReserveItem> items = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_id")
    private Rate rate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReserveStatus status;

//    @Column(name = "total_price", nullable = false)
//    private BigDecimal totalPrice;

    @Column(nullable = false,  name = "check_in")
    private LocalDate from;

    @Column(nullable = false, name = "check_out")
    private LocalDate to;

    @Column(name = "guest_first_name", nullable = false, length = 50)
    private String guestFirstName;

    @Column(name = "guest_last_name", nullable = false, length = 50)
    private String guestLastName;

    @Column(name = "guest_phone_number", nullable = false, length = 20)
    private String guestPhoneNumber;

    @Column(length = 100)
    private String description;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "transaction_id")
    private Long transactionId;
}
