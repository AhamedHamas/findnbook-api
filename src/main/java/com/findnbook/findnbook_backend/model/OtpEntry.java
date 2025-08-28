package com.findnbook.findnbook_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Document("otps")
public class OtpEntry {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;   // maps OTP to user email

    private String otp;     // 6-digit OTP code

    @Indexed(expireAfterSeconds = 0)
    private Instant expiry; // Mongo auto-deletes this document after expiry

    private Instant lastSentAt; // for resend cooldown
}