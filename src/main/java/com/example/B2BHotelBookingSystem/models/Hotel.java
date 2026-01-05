package com.example.B2BHotelBookingSystem.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_gen")
    @SequenceGenerator(name = "hotel_gen", sequenceName = "hotel_seq",allocationSize = 1)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 255)
    private String address;
    @Column(nullable = false)
    @Check(constraints = "star between 1 and 5")
    private Integer star;

    @Column(length = 15)
    private String tel;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contract> contracts = new HashSet<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rate> rates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        this.rooms.add(room);
        room.setHotel(this);
    }

    public void removeRoom(Room room){
        if (room == null){
            throw new IllegalArgumentException("Room cannot be null");
        }
        this.rooms.remove(room);
        room.setHotel(null);
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void addContract(Contract contract) {
        if (contract == null) {
            throw new IllegalArgumentException("Contract cannot be null");
        }
        this.contracts.add(contract);
        contract.setHotel(this);
    }

    public void removeContract(Contract contract) {
        if (contract == null) {
            throw new IllegalArgumentException("Contract cannot be null");
        }
        this.contracts.remove(contract);
        contract.setHotel(null);
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void addRate(Rate rate) {
        if (rate == null) {
            throw new IllegalArgumentException("Rate cannot be null");
        }
        this.rates.add(rate);
        rate.setHotel(this);
    }

    public void removeRate(Rate rate) {
        if (rate == null) {
            throw new IllegalArgumentException("Rate cannot be null");
        }
        this.rates.remove(rate);
        rate.setHotel(null);
    }

}
