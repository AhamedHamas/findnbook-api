package com.findnbook.findnbook_backend.controller;

import com.findnbook.findnbook_backend.dto.*;
import com.findnbook.findnbook_backend.enums.OtpStatus;
import com.findnbook.findnbook_backend.model.User;
import com.findnbook.findnbook_backend.repository.UserRepository;
import com.findnbook.findnbook_backend.security.JwtUtil;
import com.findnbook.findnbook_backend.service.OtpService;
import com.findnbook.findnbook_backend.service.OwnerSignupService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final JwtUtil jwtUtil;
    private final OwnerSignupService ownerSignupService;


    public AuthController(UserRepository userRepository,
                          OtpService otpService,
                          JwtUtil jwtUtil,
                          OwnerSignupService ownerSignupService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.jwtUtil = jwtUtil;
        this.ownerSignupService = ownerSignupService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "User already exists with this email.")
            );
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setRole(request.getRole() != null ? request.getRole() : "USER");

        User savedUser = userRepository.save(newUser);

        return ResponseEntity.ok(savedUser);
    }


    @PostMapping("/signup-owner")
    public ResponseEntity<?> signupOwner(@Valid @RequestBody SignupOwnerRequest request) {
        try {
            Map<String, Object> signupResult = ownerSignupService.signupOwner(request);

            User savedOwner = (User) signupResult.get("user");

            Map<String, Object> claims = new HashMap<>();
            claims.put("name", savedOwner.getName());
            claims.put("email", savedOwner.getEmail());
            claims.put("phoneNumber", savedOwner.getPhoneNumber());
            claims.put("role", savedOwner.getRole());

            String token = jwtUtil.generateToken(claims);

            UserDTO userDTO = new UserDTO(
                    savedOwner.getId(),
                    savedOwner.getName(),
                    savedOwner.getEmail(),
                    savedOwner.getPhoneNumber(),
                    savedOwner.getRole()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", userDTO);
            response.put("business", signupResult.get("business"));

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/request-otp")
    public ResponseEntity<RequestOTPResponse> requestOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (userRepository.existsByEmail(email)) {
            otpService.createAndSendOtp(email);
            return ResponseEntity.ok(new RequestOTPResponse(email, "OTP sent to your email"));
        } else {
            return ResponseEntity.status(404)
                    .body(new RequestOTPResponse(email, "User not found. Please sign up."));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody @Valid VerifyOtpRequest request) {
        OtpStatus status = otpService.verifyOtp(request.getEmail(), request.getOtp());

        if (status == OtpStatus.EXPIRED) {
            return ResponseEntity
                    .status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(Map.of("error", "OTP has expired"));
        } else if (status == OtpStatus.INCORRECT) {
            return ResponseEntity
                    .status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(Map.of("error", "OTP is incorrect"));
        } else if (status == OtpStatus.NOT_FOUND) {
            return ResponseEntity
                    .status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(Map.of("error", "No OTP found for this email"));
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("role", user.getRole());

        String token = jwtUtil.generateToken(claims);

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );

        VerifyOTPResponse response = new VerifyOTPResponse(token, userDTO);

        return ResponseEntity.ok(response);
    }
}
