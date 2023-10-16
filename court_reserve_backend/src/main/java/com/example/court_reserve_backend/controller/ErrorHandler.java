package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.dto.ErrorDto;
import com.example.court_reserve_backend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SubscriptionAlreadyExists.class)
    public ErrorDto handleSubscriptionAlreadyExists(SubscriptionAlreadyExists ex){
        return new ErrorDto("User already has a subscription in this day, month and time interval");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorDto handleUserNotFound(UserNotFoundException ex){
        return new ErrorDto("User not found!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CourtNotFoundException.class)
    public ErrorDto handleCourtNotFound(CourtNotFoundException ex){
        return new ErrorDto("Court not found!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AddressNotFoundException.class)
    public ErrorDto handleAddressNotFound(AddressNotFoundException ex){
        return new ErrorDto("Address not found!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ErrorDto handleSubscriptionNotFound(SubscriptionNotFoundException ex){
        return new ErrorDto("Subscription not found!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PriceNotFoundException.class)
    public ErrorDto handlePriceNotFound(PriceNotFoundException ex){
        return new ErrorDto("Price not found!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExists.class)
    public ErrorDto handleUserAlreadyExists(UserAlreadyExists ex){
        return new ErrorDto("User already exists!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidMonthName.class)
    public ErrorDto handleInvalidMonth(InvalidMonthName ex){
        return new ErrorDto("Invalid month name");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDayName.class)
    public ErrorDto handleInvalidDayOfWeek(InvalidDayName ex){
        return new ErrorDto("Invalid day name");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CourtOccupiedException.class)
    public ErrorDto handleCourtOccupiedException(CourtOccupiedException ex){
        return new ErrorDto("This court already has a subscription in those parameters");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TimeIntervalException.class)
    public ErrorDto handleTimeIntervalException(TimeIntervalException ex){
        return new ErrorDto("Time interval should be only between 06:00:00 - 22:00:00");
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReservationAlreadyExists.class)
    public ErrorDto handleQuestionNotFound(ReservationAlreadyExists ex){
        return new ErrorDto("Reservation already exists!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReservationNotFoundException.class)
    public ErrorDto handleQuestionNotFound(ReservationNotFoundException ex){
        return new ErrorDto("Reservation not found!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReservationNotCancelled.class)
    public ErrorDto handleQuestionNotFound(ReservationNotCancelled ex){
        return new ErrorDto("Reservation cannot be canceled with less than 24 hours in advance!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestNotFoundException.class)
    public ErrorDto handleQuestionNotFound(RequestNotFoundException ex){
        return new ErrorDto("Request not found!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestAlreadyExists.class)
    public ErrorDto handleQuestionNotFound(RequestAlreadyExists ex){
        return new ErrorDto("Request already exists!");
    }
}
