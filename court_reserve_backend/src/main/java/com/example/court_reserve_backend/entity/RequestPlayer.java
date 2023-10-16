package com.example.court_reserve_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class RequestPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_player_id", nullable = false)
    private Long id;

    @Column(name = "no_players", nullable = false)
    private int noPlayers;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    @JsonBackReference
    private Reservation reservation;

}
