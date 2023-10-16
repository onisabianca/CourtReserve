package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.dto.CourtDto;
import com.example.court_reserve_backend.service.CourtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/courts")
@RequiredArgsConstructor
public class CourtController {
    private final CourtService courtService;

    @PostMapping(value = "/{name}/{hourlyPrice}/{addressId}/{priceId}")
    public CourtDto insert(@PathVariable String name, @PathVariable double hourlyPrice, @PathVariable Long addressId, @PathVariable Long priceId)
    {
        return courtService.insert(name, hourlyPrice, addressId, priceId);
    }

    @GetMapping(value = "/{id}")
    public CourtDto findById(@PathVariable Long id){
        return courtService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id){
        courtService.remove(id);
    }

    @PutMapping("/{id}/{name}/{hourlyPrice}")
    public CourtDto update(@PathVariable Long id, @PathVariable String name, @PathVariable Double hourlyPrice){
        return courtService.update(id, name, hourlyPrice);
    }

    @GetMapping()
    public List<CourtDto> findAll(){
        return courtService.findAll();
    }
}
