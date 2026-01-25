package com.example.B2BHotelBookingSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE rates SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "rates")
public class Rate extends BaseEntity{
    @Id
    //appropriate for postgres
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rate_gen")
    //@SequenceGenerator(name = "rate_gen", sequenceName = "rate_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @OneToMany(mappedBy = "rate")
    private List<Reservation> reservations;

    @Column(nullable = false, name = "valid_from")
    private LocalDate from;

    @Column(nullable = false, name = "valid_to")
    private LocalDate to;


    @Column(name = "discount_percent", nullable = false)
    private Integer discountPercent ;

    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount;


    @AssertTrue
    public boolean isDiscountValid() {
        return (discountPercent != null) ^ (discountAmount != null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Rate)) return false;

        Rate other = (Rate) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
