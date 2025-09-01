package com.findnbook.findnbook_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BusinessResponse {
    private String id;
    private String name;
    private String address;
    private String description;
    private String openingTime;
    private String closingTime;
    private List<String> workingDays;
    private String category;
    private String ownerName;
    private String ownerEmail;
    private List<BusinessServiceResponse> services;
}