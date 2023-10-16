package com.example.court_reserve_backend.persistence.jpa;

import com.example.court_reserve_backend.entity.Address;
import com.example.court_reserve_backend.persistence.api.AddressRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateAddressFactory implements AddressRepository {

    private final EntityManager entityManager;
    @Override
    public Optional<Address> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Address.class, id));
    }

    @Override
    public List<Address> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> query = builder.createQuery(Address.class);
        query.select(query.from(Address.class));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Address save(Address address) {
        if(address.getId() != null){
            entityManager.merge(address);
        }
        else{
            entityManager.persist(address);
        }
        return address;
    }

    @Override
    public void remove(Address address) {
        Address mergeAddress = entityManager.merge(address);
        entityManager.remove(mergeAddress);
    }

    @Override
    public List<Address> findByCity(String city) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> query = builder.createQuery(Address.class);
        Root<Address> root = query.from(Address.class);
        query.select(root).where(builder.equal(root.get("city"), city));
        return entityManager.createQuery(query).getResultList();
    }
}
