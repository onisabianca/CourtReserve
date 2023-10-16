package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.dto.SubscriptionDto;
import com.example.court_reserve_backend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping(value = "/user/{id}")
    public List<SubscriptionDto> findByUserId(@PathVariable Long id){
        return subscriptionService.findByUserId(id);
    }

    @GetMapping(value = "/court/{id}")
    public List<SubscriptionDto> findByCourtId(@PathVariable Long id){return subscriptionService.findByCourtId(id);}

    @PostMapping
    public SubscriptionDto insert(@RequestBody SubscriptionDto subscriptionDto)
    {
        return subscriptionService.insert(subscriptionDto);
    }

    @GetMapping(value = "/{id}")
    public SubscriptionDto findById(@PathVariable Long id){
        return subscriptionService.findById(id);
    }

    @GetMapping(value = "/price")
    public double previewPrice(@RequestBody SubscriptionDto subscriptionDto){
        return subscriptionService.getPrice(subscriptionDto);
    }

    @DeleteMapping(value = "/{id}")
    public void remove(@PathVariable Long id){
        subscriptionService.remove(id);
    }

    @PutMapping
    public SubscriptionDto update(@RequestBody SubscriptionDto subscriptionDto){
        return subscriptionService.update(subscriptionDto);
    }
}
