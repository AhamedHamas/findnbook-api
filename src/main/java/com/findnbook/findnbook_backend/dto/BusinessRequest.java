package com.findnbook.findnbook_backend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Category is mandatory")
    private String category;

    private String description;
    private String openingTime;
    private String closingTime;
    private List<String> workingDays;

    @NotBlank(message = "OwnerId is mandatory")
    private String ownerId;
}