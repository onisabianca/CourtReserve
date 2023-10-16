package com.example.court_reserve_backend.persistence.jpa;

import com.example.court_reserve_backend.persistence.api.EmailSenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderRepository {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bianca.oni11@gmail.com");
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);

        this.mailSender.send(message);
    }
}
