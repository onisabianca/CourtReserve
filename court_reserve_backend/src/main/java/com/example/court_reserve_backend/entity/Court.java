package com.example.court_reserve_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hourly_price", nullable = false)
    private double hourlyPrice;

    @ManyToOne
    @ToString.Include
    @JsonBackReference
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "court", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonManagedReference
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "court", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonManagedReference
    private Set<Subscription> subscriptions = new HashSet<>();

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "price_id")
    private Price price;


}
