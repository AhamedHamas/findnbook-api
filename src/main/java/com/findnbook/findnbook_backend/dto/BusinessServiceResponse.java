package com.findnbook.findnbook_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessServiceResponse {
    private String id;
    private String name;
    private double price;
    private int durationMinutes;
    private String businessId;
    private String businessName;
}
