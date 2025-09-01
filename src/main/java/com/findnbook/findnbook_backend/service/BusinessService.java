package com.findnbook.findnbook_backend.service;

import com.findnbook.findnbook_backend.dto.BusinessServiceResponse;
import com.findnbook.findnbook_backend.model.Business;
import com.findnbook.findnbook_backend.repository.BusinessRepository;
import com.findnbook.findnbook_backend.repository.BusinessServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final BusinessServiceRepository businessServiceRepository;


    public BusinessService(BusinessRepository businessRepository, BusinessServiceRepository businessServiceRepository) {
        this.businessRepository = businessRepository;
        this.businessServiceRepository = businessServiceRepository;
    }

    public Business createBusiness(Business business) {
        return businessRepository.save(business);
    }


    public List<Business> getAllBusinesses(String category) {
        if (category == null || category.isEmpty()) {
            return businessRepository.findAll();
        } else {
            return businessRepository.findByCategory(category);
        }
    }

    public List<com.findnbook.findnbook_backend.model.BusinessService> getServicesForBusiness(String businessId) {
        return businessServiceRepository.findByBusinessId(businessId);
    }

    public Business getBusinessByOwner(String ownerId) {
        return businessRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Business not found for this owner"));
    }
}