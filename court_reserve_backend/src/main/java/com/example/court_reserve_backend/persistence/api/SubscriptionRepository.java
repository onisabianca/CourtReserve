package com.example.court_reserve_backend.persistence.api;

import com.example.court_reserve_backend.entity.Subscription;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository {
    Optional<Subscription> findById(Long id);
    List<Subscription> findByUserId(Long id);
    Subscription save(Subscription subscription);
    void remove(Subscription subscription);
    List<Subscription> findByCourtId(Long id);

}
