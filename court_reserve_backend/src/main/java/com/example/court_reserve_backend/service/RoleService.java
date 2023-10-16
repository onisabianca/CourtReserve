package com.example.court_reserve_backend.service;

import com.example.court_reserve_backend.entity.Role;
import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RepositoryFactory factory;

    @Transactional
    public Role findRoleById(Long id)
    {
        return factory.createRoleRepository().getRoleById(id);
    }

    @Transactional
    public String findNameById(Long id)
    {
        return factory.createRoleRepository().getNameById(id);
    }
}
