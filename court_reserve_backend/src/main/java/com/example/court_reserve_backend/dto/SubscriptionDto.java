package com.example.court_reserve_backend.dto;

import com.example.court_reserve_backend.entity.Subscription;
import lombok.*;
import java.sql.Time;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubscriptionDto {
    private Long id;
    private String month;
    private String dayOfWeek;
    private Time startTime;
    private Time endTime;
    private double subscriptionPrice;
    private Long userId;
    private Long courtId;

    public static SubscriptionDto subscriptionDtoFromSubscription(Subscription subscription){
        SubscriptionDto convert = new SubscriptionDto();
        convert.setId(subscription.getId());
        convert.setMonth(subscription.getMonth());
        convert.setDayOfWeek(subscription.getDayOfWeek());
        convert.setStartTime(subscription.getStartTime());
        convert.setEndTime(subscription.getEndTime());
        convert.setUserId(subscription.getUser().getId());
        convert.setCourtId(subscription.getCourt().getId());
        convert.setSubscriptionPrice(subscription.getSubscriptionPrice());
        return convert;
    }

    public static Subscription subscriptionFromSubscriptionDto(SubscriptionDto subscriptionDto){
        Subscription convert = new Subscription();
        convert.setId(subscriptionDto.getId());
        convert.setMonth(subscriptionDto.getMonth());
        convert.setDayOfWeek(subscriptionDto.getDayOfWeek());
        convert.setEndTime(subscriptionDto.getEndTime());
        convert.setStartTime(subscriptionDto.getStartTime());
        convert.setSubscriptionPrice(subscriptionDto.getSubscriptionPrice());
        return convert;
    }
}
