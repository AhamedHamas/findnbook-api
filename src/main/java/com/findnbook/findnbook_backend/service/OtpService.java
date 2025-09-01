package com.findnbook.findnbook_backend.service;

import com.findnbook.findnbook_backend.enums.OtpStatus;
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
        int number = 1000 + random.nextInt(9000);
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


    public OtpStatus verifyOtp(String email, String otp) {
        Optional<OtpEntry> otpEntryOpt = otpRepository.findByEmail(email);
        if (otpEntryOpt.isEmpty()) return OtpStatus.NOT_FOUND;

        OtpEntry otpEntry = otpEntryOpt.get();

        if (otpEntry.getExpiry().isBefore(Instant.now())) {
            otpRepository.deleteByEmail(email);
            return OtpStatus.EXPIRED;
        }

        if (!otpEntry.getOtp().equals(otp)) {
            return OtpStatus.INCORRECT;
        }

        otpRepository.deleteByEmail(email);
        return OtpStatus.VALID;
    }
}