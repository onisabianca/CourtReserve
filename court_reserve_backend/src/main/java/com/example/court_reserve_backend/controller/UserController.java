package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.dto.Credentials;
import com.example.court_reserve_backend.dto.UserDto;
import com.example.court_reserve_backend.dto.UserToRequestBody;
import com.example.court_reserve_backend.entity.User;
import com.example.court_reserve_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public List<UserDto> findAll()
    {
        return userService.findAll();
    }

    @GetMapping(value="/{id}")
    public UserDto findById(@PathVariable Long id)
    {
        return userService.findById(id);
    }

    @PostMapping()
    public UserDto insert(@RequestBody UserToRequestBody user) throws NoSuchAlgorithmException {
        return userService.insert(user.getUsername(),user.getPassword(),user.getEmail(),user.getFirstName(),user.getLastName(),user.getPhoneNumber(),user.getRole(),user.getAddress());
    }

    @DeleteMapping(value="/{id}")
    public void remove(@PathVariable Long id)
    {
        userService.remove(id);
    }

    @PutMapping()
    public UserDto update(@RequestBody UserToRequestBody user)
    {
        return userService.update(user.getId(), user.getUsername(),user.getPassword(),user.getEmail(),user.getFirstName(),user.getLastName(),user.getPhoneNumber(),user.getRole(),user.getAddress());
    }

    @PostMapping(value = "/login")
    public UserDto login(@RequestBody Credentials credentials)
    {
        return userService.login(credentials);
    }
}
