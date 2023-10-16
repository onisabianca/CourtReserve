package com.example.court_reserve_backend.persistence.jpa;

import com.example.court_reserve_backend.entity.RequestPlayer;
import com.example.court_reserve_backend.persistence.api.RequestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HibernateRequestFactory implements RequestRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<RequestPlayer> findById(Long id) {
        return Optional.ofNullable(entityManager.find(RequestPlayer.class, id));
    }

    @Override
    public RequestPlayer save(RequestPlayer requestPlayer) {
        if(requestPlayer.getId() != null){
            entityManager.merge(requestPlayer);
        }
        else{
            entityManager.persist(requestPlayer);
        }
        return requestPlayer;
    }

    @Override
    public void remove(RequestPlayer requestPlayer) {
        RequestPlayer mergeRequest = entityManager.merge(requestPlayer);
        entityManager.remove(mergeRequest);
    }

    @Override
    public Optional<RequestPlayer> findByReservationId(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RequestPlayer> criteriaQuery = criteriaBuilder.createQuery(RequestPlayer.class);

        Root<RequestPlayer> ec = criteriaQuery.from(RequestPlayer.class);
        Predicate reservationPredicate = criteriaBuilder.equal(ec.get("reservation"), id);
        criteriaQuery.where(reservationPredicate);

        TypedQuery<RequestPlayer> query = entityManager.createQuery(criteriaQuery);
        List<RequestPlayer> resultList = query.getResultList();

        if (!resultList.isEmpty()) {
            return Optional.of(resultList.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<RequestPlayer> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RequestPlayer> query = cb.createQuery(RequestPlayer.class);
        Root<RequestPlayer> root = query.from(RequestPlayer.class);
        query.select(root);
        TypedQuery<RequestPlayer> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
