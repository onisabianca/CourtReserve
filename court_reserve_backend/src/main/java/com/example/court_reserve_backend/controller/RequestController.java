package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.dto.RequestDto;
import com.example.court_reserve_backend.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping(value = "/{id}")
    public RequestDto findById(@PathVariable Long id)
    {
        return requestService.findById(id);
    }

    @PostMapping
    public RequestDto insert(@RequestBody RequestDto requestDto)
    {
        return requestService.insert(requestDto.getNoPlayers(),requestDto.getStatus(),requestDto.getReservationId());
    }

    @GetMapping(value = "/reservation/{id}")
    public List<RequestDto> findByReservationId(@PathVariable Long id)
    {
        return requestService.findByReservationId(id);
    }

    @GetMapping
    public List<RequestDto> findAll()
    {
        return requestService.findAll();
    }

    @GetMapping(value = "/status/{status}")
    public List<RequestDto> findByStatus(@PathVariable String status)
    {
        return requestService.findByStatus(status);
    }

    @PutMapping(value ="/{id}")
    public RequestDto update(@PathVariable Long id)
    {
        return requestService.changeStatus(id);
    }

}
