package com.findnbook.findnbook_backend.controller;

import com.findnbook.findnbook_backend.dto.BusinessDashboardResponse;
import com.findnbook.findnbook_backend.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final BookingService bookingService;

    public DashboardController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<BusinessDashboardResponse> getBusinessDashboard(@PathVariable String businessId) {
        BusinessDashboardResponse response = bookingService.getDashboardData(businessId);
        return ResponseEntity.ok(response);
    }
}
