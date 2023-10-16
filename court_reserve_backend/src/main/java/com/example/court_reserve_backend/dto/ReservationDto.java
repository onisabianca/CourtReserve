package com.example.court_reserve_backend.dto;

import com.example.court_reserve_backend.entity.Reservation;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationDto {

    private Long id;
    private Time timeStart;
    private Time timeFinal;
    private Date date;
    private double finalPrice;
    private Long userId;
    private Long courtId;
    private Long requestPlayerId;

    public static ReservationDto reservationDtoFromReservation(Reservation reservation)
    {
        ReservationDto converted = new ReservationDto();
        converted.setId(reservation.getId());
        converted.setTimeStart(reservation.getTimeStart());
        converted.setTimeFinal(reservation.getTimeFinal());
        converted.setDate(reservation.getDate());
        converted.setFinalPrice(reservation.getFinalPrice());
        converted.setCourtId(reservation.getCourt().getId());
        converted.setUserId(reservation.getUser().getId());
        return converted;
    }

    public static Reservation reservationFromReservationDto(ReservationDto reservationDto)
    {
        Reservation converted = new Reservation();
        converted.setId(reservationDto.getId());
        converted.setTimeStart(reservationDto.getTimeStart());
        converted.setTimeFinal(reservationDto.getTimeFinal());
        converted.setDate(reservationDto.getDate());
        converted.setFinalPrice(reservationDto.getFinalPrice());
        return converted;
    }
}
