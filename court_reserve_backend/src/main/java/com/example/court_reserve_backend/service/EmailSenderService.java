package com.example.court_reserve_backend.service;

import com.example.court_reserve_backend.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final RepositoryFactory factory;

    @Transactional
    public void sendEmail(String to, String subject, String body){
        factory.createEmailSenderRepository().sendEmail(to, subject, body);
    }

}

