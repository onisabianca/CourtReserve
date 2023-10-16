package com.example.court_reserve_backend.persistence.jpa;

import com.example.court_reserve_backend.entity.Price;
import com.example.court_reserve_backend.persistence.api.PriceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HibernatePriceFactory implements PriceRepository {
    private final EntityManager entityManager;


    @Override
    public Price save(Price price) {
        if(price.getId()!=null){
            entityManager.merge(price);
        }
        else{
            entityManager.persist(price);
        }
        return price;
    }

    @Override
    public List<Price> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Price> query = builder.createQuery(Price.class);
        query.select(query.from(Price.class));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Price> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Price.class, id));
    }

    @Override
    public void remove(Price price) {
        Price mergePrice = entityManager.merge(price);
        entityManager.remove(mergePrice);
    }
}
