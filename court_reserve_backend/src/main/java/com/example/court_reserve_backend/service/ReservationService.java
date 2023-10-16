package com.example.court_reserve_backend.service;

import com.example.court_reserve_backend.dto.*;
import com.example.court_reserve_backend.entity.Court;
import com.example.court_reserve_backend.entity.Reservation;
import com.example.court_reserve_backend.entity.Subscription;
import com.example.court_reserve_backend.entity.User;
import com.example.court_reserve_backend.exception.*;
import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final RepositoryFactory factory;

    @Transactional
    public List<ReservationDto> findAll()
    {
        List<ReservationDto> reservations = factory.createReservationRepository().findAll().stream().map(ReservationDto::reservationDtoFromReservation).collect(Collectors.toList());
        for(ReservationDto r: reservations)
        {
            findRequestForReservation(r);
        }
        return reservations;
    }

    @Transactional
    public ReservationDto findById(Long id)
    {
        ReservationDto found= ReservationDto.reservationDtoFromReservation(factory.createReservationRepository().findById(id).orElseThrow(ReservationNotFoundException::new));
        findRequestForReservation(found);
        return found;
    }

    @Transactional
    public List<ReservationDto> findByUserId(Long id)
    {
        User valid = factory.createUserRepository().findById(id).orElseThrow(UserNotFoundException::new);
        if(valid.getId() != null){
            return factory.createReservationRepository().findByUserId(id).stream().map(ReservationDto::reservationDtoFromReservation).collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public List<ReservationDto> findByCourtId(Long id){
        Court valid = factory.createCourtRepository().findById(id).orElseThrow(CourtNotFoundException::new);
        if(valid.getId() != null){
            return factory.createReservationRepository().findByCourtId(id).stream().map(ReservationDto::reservationDtoFromReservation).collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public void remove(Long id){
        Reservation toBeDeleted = factory.createReservationRepository().findById(id).orElseThrow(ReservationNotFoundException::new);

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDate reservationDate = toBeDeleted.getDate().toLocalDate();
        LocalTime reservationStartTime = toBeDeleted.getTimeStart().toLocalTime();

        LocalDateTime currentDateTime = LocalDateTime.of(currentDate, currentTime);
        LocalDateTime reservationStartDateTime = LocalDateTime.of(reservationDate, reservationStartTime);

        LocalDateTime twentyFourHoursEarly = currentDateTime.plusHours(24);

        if (reservationStartDateTime.isAfter(currentDateTime) && reservationStartDateTime.isBefore(twentyFourHoursEarly)) {
            throw new ReservationNotCancelled();
        }
        else factory.createReservationRepository().remove(toBeDeleted);
    }

    @Transactional
    public ReservationDto update(ReservationDto reservationDto){
        return  getFullReservation(reservationDto,0);
    }

    @Transactional
    public ReservationDto insert (ReservationDto reservationDto){
        List<Reservation> reservations = factory.createReservationRepository().findAll();
        for (Reservation s :reservations) {
            int same=reservationDto.getDate().toLocalDate().compareTo(s.getDate().toLocalDate());
            if(same==0 &&
              (reservationDto.getTimeStart().equals(s.getTimeStart()) || (reservationDto.getTimeStart().getTime()>s.getTimeStart().getTime() && reservationDto.getTimeStart().getTime()<s.getTimeFinal().getTime())) &&
              reservationDto.getCourtId().equals(s.getCourt().getId())
            ){
                throw new ReservationAlreadyExists();
            }
        }
        return getFullReservation(reservationDto,1);
    }

    private ReservationDto getFullReservation(ReservationDto reservationDto, int check) {
        Reservation toBeUpdated = ReservationDto.reservationFromReservationDto(reservationDto);
        toBeUpdated.setCourt(factory.createCourtRepository().findById(reservationDto.getCourtId()).orElseThrow(CourtNotFoundException::new));
        toBeUpdated.setUser(factory.createUserRepository().findById(reservationDto.getUserId()).orElseThrow(UserNotFoundException::new));
        toBeUpdated.setFinalPrice(getTotalPrice(reservationDto.getTimeStart(),reservationDto.getTimeFinal(),CalculatePrice.getDayOfWeekFromDate(reservationDto.getDate()),CalculatePrice.monthFromIntToString(reservationDto.getDate().getMonth()),reservationDto.getCourtId(),reservationDto.getUserId()));
        ReservationDto result= ReservationDto.reservationDtoFromReservation(factory.createReservationRepository().save(toBeUpdated));

        if(check == 1){
            User user= factory.createUserRepository().findById(result.getUserId()).orElseThrow(UserNotFoundException::new);
            Court court= factory.createCourtRepository().findById(result.getCourtId()).orElseThrow(CourtNotFoundException::new);
            sendEmail(user, court, result);
        }
        return result;
    }

    private double getTotalPrice(Time start, Time end, String day, String month, Long courtId, Long userId){

        List<SubscriptionDto> subscriptions = factory.createSubscriptionRepository().findByUserId(userId).stream().map(SubscriptionDto::subscriptionDtoFromSubscription).collect(Collectors.toList());

        for (SubscriptionDto s: subscriptions) {
            if((s.getStartTime().compareTo(start)==0) && (s.getEndTime().compareTo(end))==0 && (s.getDayOfWeek().equals(day)) && (s.getMonth().equals(month)) && (s.getCourtId().equals(courtId)))
            {
                return 0;
            }
        }

        double finalPrice = 0;
        int dayNumber = CalculatePrice.getDayNumber(day);
        int monthNumber = CalculatePrice.getMonthNumber(month);
        int yearNumber = LocalDate.now().getYear();
        double hourlyPriceSubs = getHourlyPriceReservation(start, monthNumber, dayNumber, courtId);
        int hoursForSubscription = CalculatePrice.getHoursBetween(start, end);

        finalPrice += hourlyPriceSubs ;
        finalPrice *= hoursForSubscription ;
        return finalPrice;
    }

    @Transactional
    public double getPrice(ReservationDto reservationDto){
        return getTotalPrice(reservationDto.getTimeStart(),reservationDto.getTimeFinal(),CalculatePrice.getDayOfWeekFromDate(reservationDto.getDate()),CalculatePrice.monthFromIntToString(reservationDto.getDate().getMonth()),reservationDto.getCourtId(), reservationDto.getUserId());
    }

    private double getHourlyPriceReservation(Time start, int month, int day, Long courtId){
        Court court = factory.createCourtRepository().findById(courtId).orElseThrow(CourtNotFoundException::new);
        double price = court.getHourlyPrice();

        //check season
        String season = CalculatePrice.getSeason(month);
        if(season == "summer"){
            price +=( price * court.getPrice().getSummer() / 100);
        }else if(season == "winter"){
            price +=( price * court.getPrice().getWinter() / 100);
        }

        //check time of day
        String timeOfDay = CalculatePrice.getTimeOfDay(start);
        if(timeOfDay == "morning"){
            price += (price * court.getPrice().getMorning() / 100);
        }else if(timeOfDay == "evening" ){
            price += (price * court.getPrice().getEvening() / 100);
        }
        else if(timeOfDay == "night"){
            price += (price * court.getPrice().getNight() / 100);
        }

        //check weekend days
        if(CalculatePrice.isDayOfWeekend(day)){
            price += (price * court.getPrice().getWeekend() / 100);
        }
        return price;
    }

    private void sendEmail(User user, Court court, ReservationDto reservationDto){
        String subject = "Reservation for court " + court.getName() + " successfully created!";
        String body = "Dear "+ user.getFirstName() + " "+ user.getLastName() +"\nThank you for creating a reservation! Here you can see your reservation details:\n"
                + "Email address: " + user.getEmail() + "\n"
                + "Court: " + court.getName() + "\n"
                + "Location: " + "Str. " + court.getAddress().getStreet() + ", Nr. " + court.getAddress().getNumber() + ", City " + court.getAddress().getCity()  + "\n"
                + "Reservation for: " + reservationDto.getDate() + "\n"
                + "Starting at: " + reservationDto.getTimeStart() + "\n"
                + "Ending at: " + reservationDto.getTimeFinal() + "\n"
                + "Final price: " + reservationDto.getFinalPrice();
        factory.createEmailSenderRepository().sendEmail(user.getEmail() , subject, body);
    }

    private void findRequestForReservation(ReservationDto reservationDto)
    {
        RequestDto request= factory.createRequestRepository().findByReservationId(reservationDto.getId()).map(RequestDto::requestDtoFromRequest).orElse(null);
        if(request!=null) {
            reservationDto.setRequestPlayerId(request.getId());
        }
    }
}
