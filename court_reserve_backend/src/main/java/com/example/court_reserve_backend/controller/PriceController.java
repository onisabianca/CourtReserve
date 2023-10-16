package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.entity.Price;
import com.example.court_reserve_backend.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @PostMapping()
    public Price insert(@RequestBody Price price){
        return priceService.insert(price);
    }

    @PutMapping()
    public Price update(@RequestBody Price price){
        return priceService.update(price);
    }

    @GetMapping()
    public List<Price> findAll(){
        return priceService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public void remove(@PathVariable Long id){
        priceService.remove(id);
    }

    @GetMapping(value = "/{id}")
    public Price findById(@PathVariable Long id){
        return priceService.findById(id);
    }
}
