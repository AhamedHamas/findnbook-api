package com.findnbook.findnbook_backend.service;


import com.findnbook.findnbook_backend.dto.SignupOwnerRequest;
import com.findnbook.findnbook_backend.model.Business;
import com.findnbook.findnbook_backend.model.User;
import com.findnbook.findnbook_backend.repository.BusinessRepository;
import com.findnbook.findnbook_backend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OwnerSignupService {
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    public OwnerSignupService(UserRepository userRepository, BusinessRepository businessRepository) {
        this.userRepository = userRepository;
        this.businessRepository = businessRepository;
    }

    @Transactional
    public Map<String, Object> signupOwner(SignupOwnerRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists with this email");
        }

        User owner = new User();
        owner.setName(request.getName());
        owner.setEmail(request.getEmail());
        owner.setPhoneNumber(request.getPhoneNumber());
        owner.setRole("OWNER");

        User savedOwner = userRepository.save(owner);

        Business business = new Business();
        business.setName(request.getBusinessName());
        business.setAddress(request.getAddress());
        business.setOpeningTime(request.getOpeningTime());
        business.setClosingTime(request.getClosingTime());
        business.setDescription(request.getDescription());
        business.setWorkingDays(request.getWorkingDays());
        business.setCategory(request.getCategory());
        business.setOwner(savedOwner);

        Business savedBusiness = businessRepository.save(business);

        Map<String, Object> response = new HashMap<>();
        response.put("user", savedOwner);
        response.put("business", savedBusiness);

        return response;
    }

}
