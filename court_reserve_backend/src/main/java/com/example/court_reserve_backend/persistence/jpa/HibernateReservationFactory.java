package com.example.court_reserve_backend.persistence.jpa;

import com.example.court_reserve_backend.entity.Reservation;
import com.example.court_reserve_backend.entity.Subscription;
import com.example.court_reserve_backend.persistence.api.ReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HibernateReservationFactory implements ReservationRepository {
    private final EntityManager entityManager;

    @Override
    public List<Reservation> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
        Root<Reservation> root = query.from(Reservation.class);
        query.select(root);
        TypedQuery<Reservation> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Reservation.class, id));
    }

    @Override
    public List<Reservation> findByUserId(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> criteriaQuery = criteriaBuilder.createQuery(Reservation.class);

        Root<Reservation> ec = criteriaQuery.from(Reservation.class);
        Predicate userPredicate = criteriaBuilder.equal(ec.get("user"), id);
        criteriaQuery.where(userPredicate);

        TypedQuery<Reservation> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Reservation> findByCourtId(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> criteriaQuery = criteriaBuilder.createQuery(Reservation.class);

        Root<Reservation> ec = criteriaQuery.from(Reservation.class);
        Predicate courtPredicate = criteriaBuilder.equal(ec.get("court"), id);
        criteriaQuery.where(courtPredicate);

        TypedQuery<Reservation> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        if(reservation.getId() != null){
            entityManager.merge(reservation);
        }
        else{
            entityManager.persist(reservation);
        }
        return reservation;
    }

    @Override
    public void remove(Reservation reservation) {
        Reservation mergeReservation = entityManager.merge(reservation);
        entityManager.remove(reservation);
    }
}
