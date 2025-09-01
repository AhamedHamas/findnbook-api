package com.findnbook.findnbook_backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "business_services")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessService {
    @Id
    private String id;
    private String name;
    private double price;
    private int durationMinutes;
    @DBRef
    private Business business;
}