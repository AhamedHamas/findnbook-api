package com.findnbook.findnbook_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
public class BookingResponse {
    private String bookingId;

    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private String status;

    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    private String businessId;
    private String businessName;
    private String businessAddress;

    private String serviceId;
    private String serviceName;
    private Double servicePrice;
}