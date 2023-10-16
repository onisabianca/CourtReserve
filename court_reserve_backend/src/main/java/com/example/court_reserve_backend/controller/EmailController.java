package com.example.court_reserve_backend.controller;

import com.example.court_reserve_backend.entity.EmailMessage;
import com.example.court_reserve_backend.persistence.api.EmailSenderRepository;
import com.example.court_reserve_backend.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailSenderService emailSenderService;

    @PostMapping("/send-email")
    public ResponseEntity sendEmail(@RequestBody EmailMessage emailMessage){
        emailSenderService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getBody());
        return ResponseEntity.ok("SUCCESS");
    }
}
