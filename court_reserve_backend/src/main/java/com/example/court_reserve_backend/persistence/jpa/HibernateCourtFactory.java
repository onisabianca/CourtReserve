package com.example.court_reserve_backend.persistence.jpa;


import com.example.court_reserve_backend.entity.Court;
import com.example.court_reserve_backend.persistence.api.CourtRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateCourtFactory implements CourtRepository {
    private final EntityManager entityManager;


    @Override
    public Court save(Court court) {
        if(court.getId() != null){
            entityManager.merge(court);
        }
        else{
            entityManager.persist(court);
        }
        return court;
    }

    @Override
    public List<Court> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Court> query = builder.createQuery(Court.class);
        query.select(query.from(Court.class));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Court> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Court.class, id));
    }

    @Override
    public void remove(Court court) {
        Court mergeCourt = entityManager.merge(court);
        entityManager.remove(mergeCourt);
    }
}
