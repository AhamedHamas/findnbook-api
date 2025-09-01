package com.findnbook.findnbook_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.findnbook.findnbook_backend.enums.BookingStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;

    @DBRef
    private User customer;

    @DBRef
    private Business business;

    @DBRef
    private BusinessService service;


    private LocalDate bookingDate;

    @JsonFormat(pattern = "hh:mm a")
    private LocalTime bookingTime;


    private BookingStatus status;
}