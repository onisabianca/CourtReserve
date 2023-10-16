package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.dto.ReservationDto;
import com.example.court_reserve_backend.dto.SubscriptionDto;
import com.example.court_reserve_backend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping(value = "/{id}")
    public ReservationDto findById(@PathVariable Long id)
    {
        return reservationService.findById(id);
    }

    @GetMapping(value = "/user/{id}")
    public List<ReservationDto> findByUserId(@PathVariable Long id)
    {
        return reservationService.findByUserId(id);
    }

    @GetMapping(value = "/court/{id}")
    public List<ReservationDto> findByCourtId(@PathVariable Long id)
    {
        return reservationService.findByCourtId(id);
    }

    @DeleteMapping(value ="/{id}")
    public void remove(@PathVariable Long id)
    {
        reservationService.remove(id);
    }

    @PutMapping
    public ReservationDto update(@RequestBody ReservationDto reservationDto)
    {
        return reservationService.update(reservationDto);
    }

    @PostMapping
    public ReservationDto insert(@RequestBody ReservationDto reservationDto)
    {
        return reservationService.insert(reservationDto);
    }

    @GetMapping
    public List<ReservationDto> findAll()
    {
        return reservationService.findAll();
    }
    @PostMapping(value = "/price")
    public double previewPrice(@RequestBody ReservationDto reservationDto){
        return reservationService.getPrice(reservationDto);
    }
}
