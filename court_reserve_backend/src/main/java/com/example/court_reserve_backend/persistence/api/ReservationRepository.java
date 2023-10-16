package com.example.court_reserve_backend.persistence.api;

import com.example.court_reserve_backend.entity.Reservation;
import com.example.court_reserve_backend.entity.Subscription;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository {

    List<Reservation> findAll();
    Optional<Reservation> findById(Long id);
    List<Reservation> findByUserId(Long id);
    List<Reservation> findByCourtId(Long id);
    Reservation save(Reservation reservation);
    void remove (Reservation reservation);
}
