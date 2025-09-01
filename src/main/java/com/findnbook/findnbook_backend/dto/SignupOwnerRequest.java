package com.findnbook.findnbook_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class SignupOwnerRequest {

    @NotBlank(message = "Owner name is mandatory")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Business name is mandatory")
    private String businessName;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Opening time is mandatory")
    private String openingTime;

    @NotBlank(message = "Closing time is mandatory")
    private String closingTime;

    private String description;

    @NotEmpty(message = "Working days cannot be empty")
    private List<String> workingDays;

    @NotBlank(message = "Category is mandatory")
    private String category;
}
