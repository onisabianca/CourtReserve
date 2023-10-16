package com.example.court_reserve_backend.service;

import com.example.court_reserve_backend.dto.RequestDto;
import com.example.court_reserve_backend.dto.ReservationDto;
import com.example.court_reserve_backend.entity.Court;
import com.example.court_reserve_backend.entity.RequestPlayer;
import com.example.court_reserve_backend.entity.Reservation;
import com.example.court_reserve_backend.entity.User;
import com.example.court_reserve_backend.exception.CourtNotFoundException;
import com.example.court_reserve_backend.exception.RequestAlreadyExists;
import com.example.court_reserve_backend.exception.RequestNotFoundException;
import com.example.court_reserve_backend.exception.ReservationNotFoundException;
import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RepositoryFactory factory;

    @Transactional
    public RequestDto findById(Long id){
        return RequestDto.requestDtoFromRequest(factory.createRequestRepository().findById(id).orElseThrow(RequestNotFoundException::new));
    }

    @Transactional
    public RequestDto insert (int noPlayers, String status, Long reservationId ){
        List<RequestDto> requests = factory.createRequestRepository().findAll().stream().map(RequestDto::requestDtoFromRequest).collect(Collectors.toList());

        for (RequestDto r: requests)
        {
            if (r.getReservationId()==reservationId)
                throw new RequestAlreadyExists();
        }
        RequestPlayer added= new RequestPlayer();
        added.setReservation(factory.createReservationRepository().findById(reservationId).orElseThrow(ReservationNotFoundException::new));
        added.setStatus(status);
        added.setNoPlayers(noPlayers);
        RequestDto.requestDtoFromRequest(factory.createRequestRepository().save(added));

        sendEmail(added);
        return RequestDto.requestDtoFromRequest(factory.createRequestRepository().findByReservationId(reservationId).orElseThrow(RequestNotFoundException::new));
    }

    @Transactional
    public void remove(Long id){
        RequestPlayer requestPlayer = factory.createRequestRepository().findById(id).orElseThrow(RequestNotFoundException::new);
        factory.createRequestRepository().remove(requestPlayer);
    }

    @Transactional
    public List<RequestDto> findByReservationId(Long id){
        Reservation valid= factory.createReservationRepository().findById(id).orElseThrow(ReservationNotFoundException::new);
        if(valid.getId()!=null)
        {
            return factory.createRequestRepository().findByReservationId(id).stream().map(RequestDto::requestDtoFromRequest).collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public List<RequestDto> findAll(){
        List<RequestDto> requests = factory.createRequestRepository().findAll().stream().map(RequestDto::requestDtoFromRequest).collect(Collectors.toList());
        return requests;
    }

    @Transactional
    public List<RequestDto> findByStatus(String status){
        List<RequestDto> requests = factory.createRequestRepository().findAll().stream().map(RequestDto::requestDtoFromRequest).collect(Collectors.toList());
        List<RequestDto> foundRequests= new ArrayList<RequestDto>();
        for(RequestDto r: requests)
        {
            if (r.getStatus().equals(status))
            {
                foundRequests.add(r);
            }
        }
        return foundRequests;
    }

    @Transactional
    public RequestDto changeStatus(Long id){
        RequestPlayer newRequest= factory.createRequestRepository().findById(id).orElseThrow(RequestNotFoundException::new);

        if(newRequest.getStatus().equals("AVAILABLE") && newRequest.getNoPlayers()>1)
        {
            newRequest.setNoPlayers(newRequest.getNoPlayers()-1);
            newRequest.setStatus("AVAILABLE");
            return RequestDto.requestDtoFromRequest(factory.createRequestRepository().save(newRequest));
        }
        else if (newRequest.getStatus().equals("AVAILABLE") && newRequest.getNoPlayers()==1){
            newRequest.setNoPlayers(newRequest.getNoPlayers()-1);
            newRequest.setStatus("COMPLETED");
            return RequestDto.requestDtoFromRequest(factory.createRequestRepository().save(newRequest));
        }
        else return null;
    }

    private void sendEmail(RequestPlayer request){
        Reservation reservation= factory.createReservationRepository().findById(request.getReservation().getId()).orElseThrow(ReservationNotFoundException::new);
        Court court=factory.createCourtRepository().findById(reservation.getCourt().getId()).orElseThrow(CourtNotFoundException::new);

        String subject = "Request for "+ request.getNoPlayers()+" players for court " + court.getName();
        String body = "Date: " + reservation.getDate() + "\n"
                + "Court: " + court.getName() + "\n"
                + "Location: " + "Str. " + court.getAddress().getStreet() + ", Nr. " + court.getAddress().getNumber() + ", City " + court.getAddress().getCity()  + "\n"
                + "Starting at: " + reservation.getTimeStart() + "\n"
                + "Ending at: " + reservation.getTimeFinal() + "\n"
                + "Final price: " + reservation.getFinalPrice();

        List<User> users= factory.createUserRepository().findAll();
        for(User u: users) {
            factory.createEmailSenderRepository().sendEmail(u.getEmail(), subject, body);
        }
    }
}
