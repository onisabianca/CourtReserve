package com.example.court_reserve_backend.persistence.jpa;

import com.example.court_reserve_backend.entity.Subscription;
import com.example.court_reserve_backend.persistence.api.SubscriptionRepository;
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
import java.util.Set;

@Component
@RequiredArgsConstructor
public class HibernateSubscriptionFactory implements SubscriptionRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Subscription> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Subscription.class, id));
    }

    @Override
    public List<Subscription> findByUserId(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subscription> criteriaQuery = criteriaBuilder.createQuery(Subscription.class);

        Root<Subscription> ec = criteriaQuery.from(Subscription.class);
        Predicate userPredicate = criteriaBuilder.equal(ec.get("user"), id);
        criteriaQuery.where(userPredicate);

        TypedQuery<Subscription> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Subscription save(Subscription subscription) {
        if(subscription.getId() != null){
            entityManager.merge(subscription);
        }
        else{
            entityManager.persist(subscription);
        }
        return subscription;
    }

    @Override
    public void remove(Subscription subscription) {
        Subscription mergeSubscription = entityManager.merge(subscription);
        entityManager.remove(mergeSubscription);
    }

    @Override
    public List<Subscription> findByCourtId(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subscription> criteriaQuery = criteriaBuilder.createQuery(Subscription.class);

        Root<Subscription> ec = criteriaQuery.from(Subscription.class);
        Predicate courtPredicate = criteriaBuilder.equal(ec.get("court"), id);
        criteriaQuery.where(courtPredicate);

        TypedQuery<Subscription> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
