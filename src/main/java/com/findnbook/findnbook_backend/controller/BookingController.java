package com.findnbook.findnbook_backend.controller;


import com.findnbook.findnbook_backend.dto.AvailableSlotsRequest;
import com.findnbook.findnbook_backend.dto.AvailableSlotsResponse;
import com.findnbook.findnbook_backend.dto.BookingRequest;
import com.findnbook.findnbook_backend.dto.BookingResponse;
import com.findnbook.findnbook_backend.model.Booking;
import com.findnbook.findnbook_backend.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        BookingResponse booking = bookingService.createBooking(request);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/customer/{customerId}/ongoing")
    public ResponseEntity<List<BookingResponse>> getOngoingBookings(@PathVariable String customerId) {
        return ResponseEntity.ok(bookingService.getOngoingBookings(customerId));
    }

    @GetMapping("/customer/{customerId}/previous")
    public ResponseEntity<List<BookingResponse>> getPreviousBookings(@PathVariable String customerId) {
        return ResponseEntity.ok(bookingService.getPreviousBookings(customerId));
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable String bookingId) {
        BookingResponse response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/today/{businessId}")
    public ResponseEntity<List<BookingResponse>> getTodaysBookings(@PathVariable String businessId) {
        return ResponseEntity.ok(bookingService.getTodaysBookings(businessId));
    }

    @GetMapping("/booked/{businessId}")
    public ResponseEntity<List<BookingResponse>> getBookedBookings(@PathVariable String businessId) {
        return ResponseEntity.ok(bookingService.getBookedBookingsByBusiness(businessId));
    }

    @PostMapping("/available-slots")
    public ResponseEntity<AvailableSlotsResponse> getAvailableSlots(@RequestBody AvailableSlotsRequest request) {
        return ResponseEntity.ok(
                bookingService.getAvailableSlots(
                        request.getBusinessId(),
                        request.getServiceId(),
                        request.getBookingDate()
                )
        );
    }
}
