package com.example.court_reserve_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Long id;

    @Column(name = "timeStart", nullable = false)
    private Time timeStart;

    @Column(name = "timeFinal", nullable = false)
    private Time timeFinal;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "final_price", nullable = false)
    private double finalPrice;

    @ManyToOne
    @ToString.Include
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @ToString.Include
    @JsonBackReference
    @JoinColumn(name = "court_id")
    private Court court;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private RequestPlayer requestPlayer;



}
