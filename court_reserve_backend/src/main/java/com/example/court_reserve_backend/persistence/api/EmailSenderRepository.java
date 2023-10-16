package com.example.court_reserve_backend.persistence.api;

public interface EmailSenderRepository {
    void sendEmail(String to, String subject, String body);
}
