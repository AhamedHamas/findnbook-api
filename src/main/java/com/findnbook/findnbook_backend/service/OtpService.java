package com.findnbook.findnbook_backend.service;

import com.findnbook.findnbook_backend.model.OtpEntry;
import com.findnbook.findnbook_backend.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    private final int OTP_EXPIRY_MINUTES = 5;


    private String generateOtp() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }


    public void createAndSendOtp(String email) {
        String otp = generateOtp();

        OtpEntry otpEntry = OtpEntry.builder()
                .email(email)
                .otp(otp)
                .expiry(Instant.now().plus(OTP_EXPIRY_MINUTES, ChronoUnit.MINUTES))
                .lastSentAt(Instant.now())
                .build();

        otpRepository.save(otpEntry);


        emailService.sendOtpEmail(email, otp);
    }


    public boolean verifyOtp(String email, String otp) {
        Optional<OtpEntry> otpEntryOpt = otpRepository.findByEmail(email);
        if (otpEntryOpt.isEmpty()) return false;

        OtpEntry otpEntry = otpEntryOpt.get();

        if (otpEntry.getExpiry().isBefore(Instant.now())) {
            otpRepository.deleteByEmail(email);
            return false;
        }

        boolean isValid = otpEntry.getOtp().equals(otp);

        if (isValid) {
            otpRepository.deleteByEmail(email);
        }

        return isValid;
    }
}