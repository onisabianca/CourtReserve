package com.example.court_reserve_backend.service;

import com.example.court_reserve_backend.dto.CalculatePrice;
import com.example.court_reserve_backend.dto.SubscriptionDto;
import com.example.court_reserve_backend.entity.Court;
import com.example.court_reserve_backend.entity.Subscription;
import com.example.court_reserve_backend.entity.User;
import com.example.court_reserve_backend.exception.*;
import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.round;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final RepositoryFactory factory;

    @Transactional
    public List<SubscriptionDto> findByUserId(Long id){
        User valid = factory.createUserRepository().findById(id).orElseThrow(UserNotFoundException::new);
        if(valid.getId() != null){
            return factory.createSubscriptionRepository().findByUserId(id).stream().map(SubscriptionDto::subscriptionDtoFromSubscription).collect(Collectors.toList());
        }
       return null;
    }

    @Transactional
    public List<SubscriptionDto> findByCourtId(Long id){
        Court valid = factory.createCourtRepository().findById(id).orElseThrow(CourtNotFoundException::new);
        if(valid.getId() != null){
            return factory.createSubscriptionRepository().findByCourtId(id).stream().map(SubscriptionDto::subscriptionDtoFromSubscription).collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public SubscriptionDto insert (SubscriptionDto subscriptionDto){
        validateData(subscriptionDto);
        List<Subscription> subscriptionsCourt = factory.createSubscriptionRepository().findByCourtId(subscriptionDto.getCourtId());
        for (Subscription s: subscriptionsCourt) {
            if(s.getStartTime().equals(subscriptionDto.getStartTime()) &&
                    s.getEndTime().equals(subscriptionDto.getEndTime()) &&
                    s.getDayOfWeek().equals(subscriptionDto.getDayOfWeek()) &&
                    s.getMonth().equals(subscriptionDto.getMonth()))
                throw new CourtOccupiedException();
        }
        List<Subscription> subscriptions = factory.createSubscriptionRepository().findByUserId(subscriptionDto.getUserId());
        for (Subscription s :subscriptions) {
            if (s.getStartTime().equals(subscriptionDto.getStartTime()) &&
                    s.getEndTime().equals(subscriptionDto.getEndTime()) &&
                    s.getMonth().equals(subscriptionDto.getMonth()) &&
                    s.getDayOfWeek().equals(subscriptionDto.getDayOfWeek())) {
                throw new SubscriptionAlreadyExists();
            }
        }
        return getFullSubscription(subscriptionDto, 1);

    }

    @Transactional
    public SubscriptionDto findById(Long id){
        return SubscriptionDto.subscriptionDtoFromSubscription(factory.createSubscriptionRepository().findById(id).orElseThrow(SubscriptionNotFoundException::new));
    }

    @Transactional
    public void remove(Long id){
        Subscription toBeDeleted = factory.createSubscriptionRepository().findById(id).orElseThrow(SubscriptionNotFoundException::new);
        factory.createSubscriptionRepository().remove(toBeDeleted);
    }

    @Transactional
    public SubscriptionDto update(SubscriptionDto subscriptionDto){
        validateData(subscriptionDto);
        return getFullSubscription(subscriptionDto, 0);
    }

    @Transactional
    public double getPrice(SubscriptionDto subscriptionDto){
        return getTotalPrice(subscriptionDto.getStartTime(), subscriptionDto.getEndTime(), subscriptionDto.getDayOfWeek(), subscriptionDto.getMonth(), subscriptionDto.getCourtId());
    }

    private void sendEmail(User user, Court court, SubscriptionDto subscriptionDto){
        String subject = "Subscription for court " + court.getName() + " successfully created!";
        String body = "Dear "+ user.getFirstName() + " "+ user.getLastName() +"\nThank you for creating a subscription on our site! Here you can see your subscription details:\n"
                + "Email address: " + user.getEmail() + "\n"
                + "Court: " + court.getName() + "\n"
                + "Location: " + "Str. " + court.getAddress().getStreet() + ", Nr. " + court.getAddress().getNumber() + ", City " + court.getAddress().getCity()  + "\n"
                + "Subscription for: " + subscriptionDto.getMonth() + " ,every " + subscriptionDto.getDayOfWeek() + "\n"
                + "Starting at: " + subscriptionDto.getStartTime() + "\n"
                + "Ending at: " + subscriptionDto.getEndTime() + "\n"
                + "Final price: " + subscriptionDto.getSubscriptionPrice();
        factory.createEmailSenderRepository().sendEmail(user.getEmail() , subject, body);
    }

    private SubscriptionDto getFullSubscription(SubscriptionDto subscriptionDto, int check) {
        Subscription toBeUpdated = SubscriptionDto.subscriptionFromSubscriptionDto(subscriptionDto);
        toBeUpdated.setCourt(factory.createCourtRepository().findById(subscriptionDto.getCourtId()).orElseThrow(CourtNotFoundException::new));
        toBeUpdated.setUser(factory.createUserRepository().findById(subscriptionDto.getUserId()).orElseThrow(UserNotFoundException::new));
        toBeUpdated.setSubscriptionPrice(getTotalPrice(subscriptionDto.getStartTime(), subscriptionDto.getEndTime(), subscriptionDto.getDayOfWeek(), subscriptionDto.getMonth(), subscriptionDto.getCourtId()));
        SubscriptionDto result = SubscriptionDto.subscriptionDtoFromSubscription(factory.createSubscriptionRepository().save(toBeUpdated));
        if(check == 1){
            sendEmail(toBeUpdated.getUser(), toBeUpdated.getCourt(), result);
        }
        return result;
    }

    private double getHourlyPriceSubs(Time start, int month, int day, Long courtId){
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

    private double getTotalPrice(Time start, Time end, String day, String month, Long courtId){
        double finalPrice = 0;
        int dayNumber = CalculatePrice.getDayNumber(day);
        int monthNumber = CalculatePrice.getMonthNumber(month);
        int yearNumber = LocalDate.now().getYear();
        double hourlyPriceSubs = getHourlyPriceSubs(start, monthNumber, dayNumber, courtId);
        int hoursForSubscription = CalculatePrice.getHoursBetween(start, end);
        int numberOfDaysInMonthForSubscription;
        if(Month.valueOf(month) == LocalDate.now().getMonth()){
            numberOfDaysInMonthForSubscription = CalculatePrice.countDaysInCurrentMonth(dayNumber);
        }else{
            numberOfDaysInMonthForSubscription = CalculatePrice.countDaysInMonth(dayNumber, yearNumber, monthNumber);
        }
        finalPrice += hourlyPriceSubs ;
        finalPrice *= hoursForSubscription ;
        finalPrice *= numberOfDaysInMonthForSubscription;
        finalPrice = CalculatePrice.applyDiscount(finalPrice, numberOfDaysInMonthForSubscription);
        return finalPrice;
    }

    private void validateData(SubscriptionDto subscriptionDto){
        if(CalculatePrice.checkDay(subscriptionDto.getDayOfWeek())){
            if(CalculatePrice.checkMonth(subscriptionDto.getMonth())){
                if(CalculatePrice.checkValidTimeInterval(subscriptionDto.getStartTime(), subscriptionDto.getEndTime())){
                }
                else throw new TimeIntervalException();
            }else throw new InvalidMonthName();
        }else throw new InvalidDayName();
    }



}
