package com.example.court_reserve_backend.persistence.api;

public interface RepositoryFactory {

    CourtRepository createCourtRepository();
    AddressRepository createAddressRepository();
    PriceRepository createPriceRepository();
    RoleRepository createRoleRepository();
    UserRepository createUserRepository();
    SubscriptionRepository createSubscriptionRepository();
    EmailSenderRepository createEmailSenderRepository();
	ReservationRepository createReservationRepository();
    RequestRepository createRequestRepository();

}
