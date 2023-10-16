package com.example.court_reserve_backend.persistence.api;

import com.example.court_reserve_backend.entity.Price;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository {
    Price save(Price price);
    List<Price> findAll();
    Optional<Price> findById(Long id);
    void remove(Price price);
}
