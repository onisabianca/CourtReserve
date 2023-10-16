package com.example.court_reserve_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id", nullable = false)
    private Long id;

    @Column(name = "night", nullable = false)
    private int night;

    @Column(name = "summer", nullable = false)
    private int summer;

    @Column(name = "winter", nullable = false)
    private int winter;

    @Column(name = "morning", nullable = false)
    private int morning;

    @Column(name = "evening", nullable = false)
    private int evening;

    @Column(name = "weekend", nullable = false)
    private int weekend;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "price")
    @JsonBackReference
    @JsonIgnore
    private Set<Court> courts = new HashSet<>();



}
