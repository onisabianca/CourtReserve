package com.example.court_reserve_backend.dto;

import com.example.court_reserve_backend.entity.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourtDto {
    private Long id;
    private String name;
    private Double hourlyPrice;
    private Address address;
    private Price price;
    private Set<ReservationDto> reservations = new HashSet<>();
    private Set<SubscriptionDto> subscriptions = new HashSet<>();

    public static CourtDto courtDtoFromCourt(Court court){
        CourtDto convert = new CourtDto();
        convert.setId(court.getId());
        convert.setHourlyPrice(court.getHourlyPrice());
        convert.setName(court.getName());
        convert.setAddress(court.getAddress());
        convert.setPrice(court.getPrice());

        return convert;
    }

    public static Court courtFromCourtDto(CourtDto courtDto){
        Court convert = new Court();
        convert.setId(courtDto.getId());
        convert.setHourlyPrice(courtDto.getHourlyPrice());
        convert.setName(courtDto.getName());
        convert.setPrice(courtDto.getPrice());
        convert.setAddress(courtDto.getAddress());
        return convert;
    }


}
