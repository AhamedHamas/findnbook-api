package com.findnbook.findnbook_backend.service;

import com.findnbook.findnbook_backend.dto.BusinessServiceRequest;
import com.findnbook.findnbook_backend.model.Business;
import com.findnbook.findnbook_backend.model.BusinessService;
import com.findnbook.findnbook_backend.repository.BusinessRepository;
import com.findnbook.findnbook_backend.repository.BusinessServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessServiceService {

    private final BusinessServiceRepository serviceRepository;
    private final BusinessRepository businessRepository;

    public BusinessServiceService(BusinessServiceRepository serviceRepository,
                                  BusinessRepository businessRepository) {
        this.serviceRepository = serviceRepository;
        this.businessRepository = businessRepository;
    }


    public BusinessService createService(BusinessServiceRequest request) {
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        BusinessService service = new BusinessService();
        service.setName(request.getName());
        service.setPrice(request.getPrice());
        service.setDurationMinutes(request.getDurationMinutes());
        service.setBusiness(business);

        return serviceRepository.save(service);
    }

    public List<BusinessService> createServicesBulk(List<BusinessServiceRequest> requests) {
        return requests.stream().map(this::createService).collect(Collectors.toList());
    }

    public List<BusinessService> getServicesByBusinessId(String businessId) {
        return serviceRepository.findByBusinessId(businessId);
    }

    public void deleteServiceById(String serviceId) {
        if (!serviceRepository.existsById(serviceId)) {
            throw new RuntimeException("Service with id " + serviceId + " not found");
        }
        serviceRepository.deleteById(serviceId);
    }
}