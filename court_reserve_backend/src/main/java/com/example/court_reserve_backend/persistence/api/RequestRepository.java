package com.example.court_reserve_backend.persistence.api;

import com.example.court_reserve_backend.entity.RequestPlayer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository {
    Optional<RequestPlayer> findById(Long id);
    RequestPlayer save(RequestPlayer requestPlayer);
    void remove(RequestPlayer requestPlayer);
    Optional<RequestPlayer> findByReservationId(Long id);
    List<RequestPlayer> findAll();
}
