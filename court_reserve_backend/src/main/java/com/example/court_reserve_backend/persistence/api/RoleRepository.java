package com.example.court_reserve_backend.persistence.api;

import com.example.court_reserve_backend.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {
    Role getRoleById(Long id);
    String getNameById(Long id);
}
