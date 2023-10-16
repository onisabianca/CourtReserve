package com.example.court_reserve_backend.persistence.jpa;

import com.example.court_reserve_backend.persistence.api.AddressRepository;
import com.example.court_reserve_backend.persistence.api.CourtRepository;
import com.example.court_reserve_backend.persistence.api.PriceRepository;
import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import com.example.court_reserve_backend.persistence.api.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HibernateRepositoryFactory implements RepositoryFactory {

    private final EntityManager entityManager;
    private final JavaMailSender mailSender;
    @Override
    public CourtRepository createCourtRepository() {
        return new HibernateCourtFactory(entityManager);
    }

    @Override
    public AddressRepository createAddressRepository() {
        return new HibernateAddressFactory(entityManager);
    }

    @Override
    public PriceRepository createPriceRepository() {
        return new HibernatePriceFactory(entityManager);
    }

    @Override
    public RoleRepository createRoleRepository() {
        return new HibernateRoleFactory(entityManager);
    }

    @Override
    public UserRepository createUserRepository() {
        return new HibernateUserFactory(entityManager);
    }

    @Override
    public SubscriptionRepository createSubscriptionRepository() {
        return new HibernateSubscriptionFactory(entityManager);
    }
    @Override
    public ReservationRepository createReservationRepository() {
        return new HibernateReservationFactory(entityManager);
    }

    @Override
    public EmailSenderRepository createEmailSenderRepository() {
        return new EmailSenderServiceImpl(mailSender);
    }

    @Override
    public RequestRepository createRequestRepository(){
        return new HibernateRequestFactory(entityManager);
    }
}
