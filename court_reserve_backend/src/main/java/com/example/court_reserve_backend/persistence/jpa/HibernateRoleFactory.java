package com.example.court_reserve_backend.persistence.jpa;

import com.example.court_reserve_backend.entity.Role;
import com.example.court_reserve_backend.persistence.api.RoleRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HibernateRoleFactory implements RoleRepository {

    private final EntityManager entityManager;

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public String getNameById(Long id) {
        Role role= entityManager.find(Role.class, id);
        return role.getType();
    }
}
