package com.findnbook.findnbook_backend.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingRequest {
    private String customerId;
    private String businessId;
    private String serviceId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
}