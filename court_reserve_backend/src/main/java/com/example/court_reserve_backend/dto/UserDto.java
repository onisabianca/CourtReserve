package com.example.court_reserve_backend.dto;

import com.example.court_reserve_backend.entity.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private Role role;
    private Address address;
    Set<ReservationDto> reservations = new HashSet<>();
    Set<SubscriptionDto> subscriptions = new HashSet<>();

    public static UserDto userDtoFromUser (User user)
    {
        UserDto userDto= new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole());
        userDto.setAddress(user.getAddress());

        return userDto;
    }

    public static User userFromUserDto (UserDto userDto)
    {
        User user= new User();

        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole(userDto.getRole());
        user.setAddress(userDto.getAddress());

        return user;
    }
}
