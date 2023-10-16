package com.example.court_reserve_backend.dto;

import com.example.court_reserve_backend.entity.Address;
import com.example.court_reserve_backend.entity.Court;
import com.example.court_reserve_backend.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDto {
    private Long id;
    private String city;
    private String street;
    private String number;
    Set<User> users = new HashSet<>();
    Set<Court> courts = new HashSet<>();

    public static AddressDto addressDtoFromAddress(Address address)
    {
        AddressDto addressDto= new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setNumber(address.getNumber());

        return addressDto;
    }

    public static Address addressFromAddressDto(AddressDto addressDto)
    {
        Address address= new Address();
        address.setId(addressDto.getId());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setNumber(addressDto.getNumber());

        return address;
    }
}
