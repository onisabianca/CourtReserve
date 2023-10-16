package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.entity.Role;
import com.example.court_reserve_backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping(value="/{id}")
    public Role findRoleById(@PathVariable Long id)
    {
        return roleService.findRoleById(id);
    }

    @GetMapping(value="/name/{id}")
    public String findNameById(@PathVariable Long id)
    {
        return roleService.findNameById(id);
    }
}
