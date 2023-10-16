package com.example.court_reserve_backend.service;

import com.example.court_reserve_backend.dto.CourtDto;
import com.example.court_reserve_backend.dto.ReservationDto;
import com.example.court_reserve_backend.dto.SubscriptionDto;
import com.example.court_reserve_backend.entity.Court;
import com.example.court_reserve_backend.exception.AddressNotFoundException;
import com.example.court_reserve_backend.exception.CourtNotFoundException;
import com.example.court_reserve_backend.exception.PriceNotFoundException;
import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtService {
    private final RepositoryFactory factory;

    @Transactional
    public CourtDto insert(String name, Double hourlyPrice, Long addressId, Long priceId){
        Court added = new Court();
        added.setAddress(factory.createAddressRepository().findById(addressId).orElseThrow(AddressNotFoundException::new));
        added.setPrice(factory.createPriceRepository().findById(priceId).orElseThrow(PriceNotFoundException::new));
        added.setName(name);
        added.setHourlyPrice(hourlyPrice);
        return CourtDto.courtDtoFromCourt(factory.createCourtRepository().save(added));
    }

    @Transactional
    public CourtDto findById(Long id){
        CourtDto found = CourtDto.courtDtoFromCourt(factory.createCourtRepository().findById(id).orElseThrow(CourtNotFoundException::new));
        getSubscriptionAsSet(found);
        getReservationAsSet(found);
        return found;

    }

    @Transactional
    public void remove(Long id){
        Court court = factory.createCourtRepository().findById(id).orElseThrow(CourtNotFoundException::new);
        factory.createCourtRepository().remove(court);
    }

    @Transactional
    public CourtDto update(Long id, String name, Double hourlyPrice){
        Court updateCourt = factory.createCourtRepository().findById(id).orElseThrow(CourtNotFoundException::new);
        updateCourt.setHourlyPrice(hourlyPrice);
        updateCourt.setName(name);
        CourtDto returnedCourt = CourtDto.courtDtoFromCourt(factory.createCourtRepository().save(updateCourt));
        returnedCourt.setAddress(factory.createAddressRepository().findById(updateCourt.getAddress().getId()).orElseThrow(AddressNotFoundException::new));
        returnedCourt.setPrice(factory.createPriceRepository().findById(updateCourt.getPrice().getId()).orElseThrow(PriceNotFoundException::new));
        return returnedCourt;
    }

    @Transactional
    public List<CourtDto> findAll(){
        List<CourtDto> courts = factory.createCourtRepository().findAll().stream().map(CourtDto::courtDtoFromCourt).collect(Collectors.toList());
        for (CourtDto c: courts) {
            getSubscriptionAsSet(c);
            getReservationAsSet(c);
        }
        return courts;
    }

    private void getSubscriptionAsSet(CourtDto c) {
        List<SubscriptionDto> subscriptions = factory.createSubscriptionRepository().findByCourtId(c.getId()).stream().map(SubscriptionDto::subscriptionDtoFromSubscription).collect(Collectors.toList());
        Set<SubscriptionDto> courtSet = new HashSet<>();
        courtSet.addAll(subscriptions);
        c.setSubscriptions(courtSet);
    }

    private void getReservationAsSet(CourtDto c) {
        List<ReservationDto> reservations = factory.createReservationRepository().findByCourtId(c.getId()).stream().map(ReservationDto::reservationDtoFromReservation).collect(Collectors.toList());
        Set<ReservationDto> courtSet = new HashSet<>();
        courtSet.addAll(reservations);
        c.setReservations(courtSet);
    }

}
