package com.findnbook.findnbook_backend.repository;

import com.findnbook.findnbook_backend.enums.BookingStatus;
import com.findnbook.findnbook_backend.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByCustomerId(String userId);
    List<Booking> findByBusinessId(String businessId);


    List<Booking> findByCustomerIdAndStatus(String customerId, BookingStatus status);

    List<Booking> findByCustomerIdAndStatusIn(String customerId, List<BookingStatus> statuses);


    long countByBusinessIdAndStatus(String businessId, BookingStatus status);
    List<Booking> findByBusinessIdAndStatus(String businessId, BookingStatus status);

    List<Booking> findByBusinessIdAndBookingDate(String businessId, LocalDate bookingDate);
}