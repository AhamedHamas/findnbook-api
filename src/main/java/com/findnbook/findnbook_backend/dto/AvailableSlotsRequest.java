package com.findnbook.findnbook_backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailableSlotsRequest {
    private String businessId;
    private String serviceId;
    private LocalDate bookingDate;
}