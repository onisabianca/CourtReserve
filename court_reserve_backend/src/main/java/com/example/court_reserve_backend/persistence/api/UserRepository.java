package com.example.court_reserve_backend.persistence.api;

import com.example.court_reserve_backend.dto.Credentials;
import com.example.court_reserve_backend.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    void remove (User user);
}
