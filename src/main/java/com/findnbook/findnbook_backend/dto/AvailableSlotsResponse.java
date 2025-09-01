package com.findnbook.findnbook_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AvailableSlotsResponse {
    private List<LocalTime> availableSlots;
}