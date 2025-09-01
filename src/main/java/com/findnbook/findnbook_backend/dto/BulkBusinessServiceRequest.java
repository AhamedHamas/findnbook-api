package com.findnbook.findnbook_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkBusinessServiceRequest {
    private List<BusinessServiceRequest> services;
}