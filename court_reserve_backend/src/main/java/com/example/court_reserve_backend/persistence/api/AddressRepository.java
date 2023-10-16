package com.example.court_reserve_backend.persistence.api;

import com.example.court_reserve_backend.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository {
    Optional<Address> findById(Long id);
    List<Address> findAll();
    Address save(Address address);
    void remove (Address address);
    List<Address> findByCity(String city);

}
