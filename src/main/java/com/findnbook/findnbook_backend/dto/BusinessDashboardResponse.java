package com.findnbook.findnbook_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDashboardResponse {
    private long completedBookings;
    private long upcomingBookings;
    private double totalRevenue;
}