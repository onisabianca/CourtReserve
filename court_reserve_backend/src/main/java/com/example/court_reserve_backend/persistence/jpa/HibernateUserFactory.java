package com.example.court_reserve_backend.persistence.jpa;

import com.example.court_reserve_backend.dto.Credentials;
import com.example.court_reserve_backend.entity.User;
import com.example.court_reserve_backend.persistence.api.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateUserFactory implements UserRepository {

    private final EntityManager entityManager;

    @Override
    public User save(User user) {
        if(user.getId() != null){
            entityManager.merge(user);
        }
        else{
            entityManager.persist(user);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.select(query.from(User.class));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class,id));
    }

    @Override
    public void remove(User user) {
        User mergeUser = entityManager.merge(user);
        entityManager.remove(mergeUser);
    }


}
