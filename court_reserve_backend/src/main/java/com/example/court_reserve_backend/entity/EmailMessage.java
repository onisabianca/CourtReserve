package com.example.court_reserve_backend.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailMessage {

    private String to;
    private String subject;
    private String body;
}
