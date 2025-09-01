package com.findnbook.findnbook_backend.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "businesses")
public class Business {
    @Id
    private String id;
    private String name;
    private String address;
    private String description;
    private String openingTime;
    private String closingTime;
    private List<String> workingDays;
    private String category;

    @DBRef
    private User owner;

    @DBRef
    private List<BusinessService> services;
}