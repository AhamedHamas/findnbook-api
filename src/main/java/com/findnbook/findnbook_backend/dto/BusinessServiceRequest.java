package com.findnbook.findnbook_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessServiceRequest {
    private String name;
    private double price;
    private int durationMinutes;
    private String businessId;
}
