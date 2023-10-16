package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.dto.AddressDto;
import com.example.court_reserve_backend.dto.CourtDto;
import com.example.court_reserve_backend.entity.Address;
import com.example.court_reserve_backend.service.AddressService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping()
    public List<AddressDto> findAll() {
        return addressService.findAll();
    }

    @GetMapping(value = "/{id}")
    public AddressDto findById(@PathVariable Long id){
        return addressService.findById(id);
    }

    @PostMapping()
    public AddressDto insert(@RequestBody Address address){
        return addressService.insert(address.getCity(),address.getStreet(),address.getNumber());
    }

    @DeleteMapping(value="/{id}")
    public void remove (@PathVariable Long id)
    {
        addressService.remove(id);
    }

    @PutMapping()
    public AddressDto update(@RequestBody Address address){
        return addressService.update(address.getId(),address.getCity(),address.getStreet(),address.getNumber());
    }

    @GetMapping(value="/find/{city}")
    public List<AddressDto> findByCity(@PathVariable String city){
        return addressService.findByCity(city);
    }
}
