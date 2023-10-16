package com.example.court_reserve_backend.service;

import com.example.court_reserve_backend.dto.AddressDto;
import com.example.court_reserve_backend.entity.Address;
import com.example.court_reserve_backend.exception.AddressNotFoundException;
import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final RepositoryFactory factory;
    @Transactional
    public AddressDto findById(Long id){
        return AddressDto.addressDtoFromAddress(factory.createAddressRepository().findById(id).orElseThrow(AddressNotFoundException::new));
    }

    @Transactional
    public List<AddressDto> findAll(){
        return factory.createAddressRepository().findAll().stream().map(AddressDto::addressDtoFromAddress).collect(Collectors.toList());
    }

    @Transactional
    public AddressDto insert(String city, String street, String number){
        Address addressAdded = new Address();
        addressAdded.setCity(city);
        addressAdded.setStreet(street);
        addressAdded.setNumber(number);
        return AddressDto.addressDtoFromAddress(factory.createAddressRepository().save(addressAdded));
    }

    @Transactional
    public AddressDto update(Long id, String city, String street, String number){
        Address addressUpdate= factory.createAddressRepository().findById(id).orElseThrow(AddressNotFoundException::new);
        addressUpdate.setCity(city);
        addressUpdate.setStreet(street);
        addressUpdate.setNumber(number);
        AddressDto addressDto=AddressDto.addressDtoFromAddress(factory.createAddressRepository().save(addressUpdate));
        return addressDto;
    }

    @Transactional
    public void remove(Long id){
        Address address = factory.createAddressRepository().findById(id).orElseThrow(AddressNotFoundException::new);
        factory.createAddressRepository().remove(address);
    }
    @Transactional
    public List<AddressDto> findByCity(String city){
        return factory.createAddressRepository().findByCity(city).stream().map(AddressDto::addressDtoFromAddress).collect(Collectors.toList());
    }
}
