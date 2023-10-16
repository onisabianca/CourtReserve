package com.example.court_reserve_backend.dto;

import com.example.court_reserve_backend.entity.RequestPlayer;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDto {

    private Long id;
    private int noPlayers;
    private String status;
    private Long reservationId;

    public static RequestDto requestDtoFromRequest(RequestPlayer request)
    {
        RequestDto convert = new RequestDto();
        convert.setId(request.getId());
        convert.setNoPlayers(request.getNoPlayers());
        convert.setStatus(request.getStatus());
        convert.setReservationId(request.getReservation().getId());
        return convert;
    }

    public static RequestPlayer requestFromRequestDto(RequestDto requestDto)
    {
        RequestPlayer convert = new RequestPlayer();
        convert.setId(requestDto.getId());
        convert.setNoPlayers(requestDto.getNoPlayers());
        convert.setStatus(requestDto.getStatus());
        return convert;
    }
}
