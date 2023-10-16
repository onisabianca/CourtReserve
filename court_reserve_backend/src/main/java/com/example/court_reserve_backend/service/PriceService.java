package com.example.court_reserve_backend.service;

import com.example.court_reserve_backend.entity.Price;
import com.example.court_reserve_backend.exception.PriceNotFoundException;
import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final RepositoryFactory factory;

    @Transactional
    public Price insert(Price price){
        return factory.createPriceRepository().save(price);
    }

    @Transactional
    public Price update(Price price){
        return factory.createPriceRepository().save(price);
    }

    @Transactional
    public void remove(Long id){
        Price deletePrice = factory.createPriceRepository().findById(id).orElseThrow(PriceNotFoundException::new);
        factory.createPriceRepository().remove(deletePrice);
    }

    @Transactional
    public Price findById(Long id){
        return factory.createPriceRepository().findById(id).orElseThrow(PriceNotFoundException::new);
    }

    @Transactional
    public List<Price> findAll(){
        return factory.createPriceRepository().findAll();
    }

}
