package com.findnbook.findnbook_backend.controller;

import com.findnbook.findnbook_backend.dto.BulkBusinessServiceRequest;

import com.findnbook.findnbook_backend.dto.BusinessServiceRequest;

import com.findnbook.findnbook_backend.dto.BusinessServiceResponse;
import com.findnbook.findnbook_backend.model.BusinessService;
import com.findnbook.findnbook_backend.service.BusinessServiceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/services")
@CrossOrigin(origins = "*")
public class BusinessServiceController {

    private final BusinessServiceService businessServiceService;

    public BusinessServiceController(BusinessServiceService businessServiceService) {
        this.businessServiceService = businessServiceService;
    }

    @PostMapping
    public ResponseEntity<BusinessServiceResponse> createService(@RequestBody @Valid BusinessServiceRequest request) {
        BusinessService savedService = businessServiceService.createService(request);

        BusinessServiceResponse response = new BusinessServiceResponse(
                savedService.getId(),
                savedService.getName(),
                savedService.getPrice(),
                savedService.getDurationMinutes(),
                savedService.getBusiness().getId(),
                savedService.getBusiness().getName()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<BusinessService>> createServicesBulk(@RequestBody @Valid BulkBusinessServiceRequest request) {
        List<BusinessService> savedServices = businessServiceService.createServicesBulk(request.getServices());
        return ResponseEntity.ok(savedServices);
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<List<BusinessServiceResponse>> getServicesByBusinessId(@PathVariable String businessId) {
        List<BusinessService> services = businessServiceService.getServicesByBusinessId(businessId);

        List<BusinessServiceResponse> responseList = services.stream()
                .map(service -> new BusinessServiceResponse(
                        service.getId(),
                        service.getName(),
                        service.getPrice(),
                        service.getDurationMinutes(),
                        service.getBusiness().getId(),
                        service.getBusiness().getName()
                ))
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Map<String, Object>> deleteService(@PathVariable String serviceId) {
        businessServiceService.deleteServiceById(serviceId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Service deleted successfully");
        response.put("status", 200);

        return ResponseEntity.ok(response);
    }
}
