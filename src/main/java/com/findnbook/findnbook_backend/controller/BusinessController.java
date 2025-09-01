package com.findnbook.findnbook_backend.controller;

import com.findnbook.findnbook_backend.dto.BusinessRequest;
import com.findnbook.findnbook_backend.dto.BusinessResponse;
import com.findnbook.findnbook_backend.dto.BusinessServiceResponse;
import com.findnbook.findnbook_backend.model.Business;
import com.findnbook.findnbook_backend.model.User;
import com.findnbook.findnbook_backend.service.BusinessService;
import com.findnbook.findnbook_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/businesses")
@CrossOrigin(origins = "*")
public class BusinessController {

    private final BusinessService businessService;
    private final UserService userService;

    public BusinessController(BusinessService businessService, UserService userService) {
        this.businessService = businessService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createBusiness(@Valid @RequestBody BusinessRequest request) {
        try {

            User owner = userService.getUserByIdOrThrow(request.getOwnerId());


            Business business = Business.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .category(request.getCategory())
                    .description(request.getDescription())
                    .openingTime(request.getOpeningTime())
                    .closingTime(request.getClosingTime())
                    .workingDays(request.getWorkingDays())
                    .owner(owner)
                    .build();

            Business savedBusiness = businessService.createBusiness(business);

            return ResponseEntity.ok(savedBusiness);

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }



    @GetMapping
    public ResponseEntity<List<BusinessResponse>> getAllBusinesses(
            @RequestParam(required = false) String category) {

        List<BusinessResponse> businesses = businessService.getAllBusinesses(category).stream()
                .map(b -> {
                    List<BusinessServiceResponse> serviceResponses =
                            businessService.getServicesForBusiness(b.getId()).stream()
                                    .map(s -> new BusinessServiceResponse(
                                            s.getId(),
                                            s.getName(),
                                            s.getPrice(),
                                            s.getDurationMinutes(),
                                            b.getId(),
                                            b.getName()
                                    ))
                                    .collect(Collectors.toList());

                    return new BusinessResponse(
                            b.getId(),
                            b.getName(),
                            b.getAddress(),
                            b.getDescription(),
                            b.getOpeningTime(),
                            b.getClosingTime(),
                            b.getWorkingDays(),
                            b.getCategory(),
                            b.getOwner().getName(),
                            b.getOwner().getEmail(),
                            serviceResponses
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(businesses);
    }



    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<BusinessResponse> getBusinessByOwner(@PathVariable String ownerId) {

        Business b = businessService.getBusinessByOwner(ownerId);

        List<BusinessServiceResponse> serviceResponses =
                businessService.getServicesForBusiness(b.getId()).stream()
                        .map(s -> new BusinessServiceResponse(
                                s.getId(),
                                s.getName(),
                                s.getPrice(),
                                s.getDurationMinutes(),
                                b.getId(),
                                b.getName()
                        ))
                        .collect(Collectors.toList());

        BusinessResponse response = new BusinessResponse(
                b.getId(),
                b.getName(),
                b.getAddress(),
                b.getDescription(),
                b.getOpeningTime(),
                b.getClosingTime(),
                b.getWorkingDays(),
                b.getCategory(),
                b.getOwner().getName(),
                b.getOwner().getEmail(),
                serviceResponses
        );

        return ResponseEntity.ok(response);
    }
}
