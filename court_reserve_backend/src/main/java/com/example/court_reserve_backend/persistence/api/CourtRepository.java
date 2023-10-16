package com.example.court_reserve_backend.persistence.api;

import com.example.court_reserve_backend.entity.Court;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourtRepository {
    Court save(Court court);
    List<Court> findAll();
    Optional<Court> findById(Long id);
    void remove(Court court);
}
