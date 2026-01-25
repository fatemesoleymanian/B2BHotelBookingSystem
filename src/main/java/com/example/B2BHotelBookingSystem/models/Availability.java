package com.example.B2BHotelBookingSystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "availabilities",
        indexes = {
                @Index(name = "idx_av_room_date", columnList = "room_id, available_at"),
                @Index(name = "idx_av_deleted", columnList = "deleted_at")
        })
@SQLDelete(sql = "UPDATE availabilities SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Availability extends BaseEntity{
    @Id
    //appropriate for postgres
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availability_gen")
    //@SequenceGenerator(name = "availability_gen", sequenceName = "availability_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "available_at", nullable = false)
    private LocalDate availableAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoomStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "room_type")
    //For Performance
    private RoomType roomType;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Availability)) return false;

        Availability other = (Availability) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
